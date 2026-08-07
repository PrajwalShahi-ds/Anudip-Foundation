package com.bank.service;

import com.bank.dao.UserDAO;
import com.bank.entity.User;
import com.bank.util.JPAUtil;
import com.bank.util.PasswordUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public User registerUser(String username, String password, String fullName, String email) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (userDAO.findByUsername(em, username).isPresent()) {
                throw new IllegalArgumentException("Username '" + username + "' is already taken.");
            }

            if (userDAO.findByEmail(em, email).isPresent()) {
                throw new IllegalArgumentException("Email '" + email + "' is already registered.");
            }

            String hashedPassword = PasswordUtil.hashPassword(password);
            User user = new User(username, hashedPassword, fullName, email);
            userDAO.save(em, user);

            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public User loginUser(String username, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Optional<User> userOpt = userDAO.findByUsername(em, username);
            if (userOpt.isEmpty()) {
                throw new IllegalArgumentException("Invalid username or password.");
            }

            User user = userOpt.get();
            if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
                throw new IllegalArgumentException("Invalid username or password.");
            }

            return user;
        } finally {
            em.close();
        }
    }
}
