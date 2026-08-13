package com.bank.dao;

import com.bank.entity.Transaction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TransactionDAO {

    public void save(EntityManager em, Transaction transaction) {
        em.persist(transaction);
    }

    public List<Transaction> findByAccountId(EntityManager em, Long accountId) {
        TypedQuery<Transaction> query = em.createQuery(
            "SELECT t FROM Transaction t WHERE t.account.id = :accountId ORDER BY t.timestamp DESC", Transaction.class);
        query.setParameter("accountId", accountId);
        return query.getResultList();
    }
}
