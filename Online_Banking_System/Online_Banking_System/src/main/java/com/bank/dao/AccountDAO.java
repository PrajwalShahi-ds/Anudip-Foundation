package com.bank.dao;

import com.bank.entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class AccountDAO {

    public void save(EntityManager em, Account account) {
        em.persist(account);
    }

    public Account update(EntityManager em, Account account) {
        return em.merge(account);
    }

    public Optional<Account> findById(EntityManager em, Long id) {
        Account account = em.find(Account.class, id);
        return Optional.ofNullable(account);
    }

    public Optional<Account> findByAccountNumber(EntityManager em, String accountNumber) {
        try {
            TypedQuery<Account> query = em.createQuery(
                "SELECT a FROM Account a JOIN FETCH a.user WHERE a.accountNumber = :accNum", Account.class);
            query.setParameter("accNum", accountNumber);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Account> findByUserId(EntityManager em, Long userId) {
        TypedQuery<Account> query = em.createQuery(
            "SELECT a FROM Account a WHERE a.user.id = :userId ORDER BY a.id ASC", Account.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
