package com.bank.service;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.dao.UserDAO;
import com.bank.entity.*;
import com.bank.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class BankingService {

    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final UserDAO userDAO = new UserDAO();

    public Account createAccount(Long userId, AccountType accountType, BigDecimal initialDeposit) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            User user = userDAO.findById(em, userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

            String accountNumber = generateAccountNumber();
            Account account = new Account(accountNumber, accountType, initialDeposit, user);
            accountDAO.save(em, account);

            if (initialDeposit != null && initialDeposit.compareTo(BigDecimal.ZERO) > 0) {
                Transaction initialTransaction = new Transaction(
                        account, TransactionType.DEPOSIT, initialDeposit, "Initial Deposit", null);
                transactionDAO.save(em, initialTransaction);
            }

            tx.commit();
            return account;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Account account = accountDAO.findByAccountNumber(em, accountNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

            account.deposit(amount);
            accountDAO.update(em, account);

            Transaction transaction = new Transaction(
                    account, TransactionType.DEPOSIT, amount, "Deposit into account", null);
            transactionDAO.save(em, transaction);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Account account = accountDAO.findByAccountNumber(em, accountNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));

            account.withdraw(amount);
            accountDAO.update(em, account);

            Transaction transaction = new Transaction(
                    account, TransactionType.WITHDRAWAL, amount, "Withdrawal from account", null);
            transactionDAO.save(em, transaction);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void transferMoney(String senderAccountNumber, String recipientAccountNumber, BigDecimal amount) {
        if (senderAccountNumber.equalsIgnoreCase(recipientAccountNumber)) {
            throw new IllegalArgumentException("Sender and recipient account cannot be the same.");
        }

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Account senderAccount = accountDAO.findByAccountNumber(em, senderAccountNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Sender account not found: " + senderAccountNumber));

            Account recipientAccount = accountDAO.findByAccountNumber(em, recipientAccountNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Recipient account not found: " + recipientAccountNumber));

            // Perform money transfer
            senderAccount.withdraw(amount);
            recipientAccount.deposit(amount);

            accountDAO.update(em, senderAccount);
            accountDAO.update(em, recipientAccount);

            // Record transaction for Sender
            Transaction senderTx = new Transaction(
                    senderAccount, TransactionType.TRANSFER_OUT, amount,
                    "Transfer to " + recipientAccountNumber, recipientAccountNumber);
            transactionDAO.save(em, senderTx);

            // Record transaction for Recipient
            Transaction recipientTx = new Transaction(
                    recipientAccount, TransactionType.TRANSFER_IN, amount,
                    "Transfer from " + senderAccountNumber, senderAccountNumber);
            transactionDAO.save(em, recipientTx);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Account> getUserAccounts(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return accountDAO.findByUserId(em, userId);
        } finally {
            em.close();
        }
    }

    public List<Transaction> getAccountTransactions(String accountNumber) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Account account = accountDAO.findByAccountNumber(em, accountNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountNumber));
            return transactionDAO.findByAccountId(em, account.getId());
        } finally {
            em.close();
        }
    }

    private String generateAccountNumber() {
        Random random = new Random();
        long num = 1000000000L + (long) (random.nextDouble() * 9000000000L);
        return "ACC" + num;
    }
}
