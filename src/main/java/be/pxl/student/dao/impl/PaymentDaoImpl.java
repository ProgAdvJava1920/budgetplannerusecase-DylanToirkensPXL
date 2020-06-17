package be.pxl.student.dao.impl;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class PaymentDaoImpl implements PaymentDao {
    private static final Logger LOGGER = LogManager.getLogger(AccountDao.class);

    private EntityManager entityManager;

    public PaymentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Payment findPaymentById(long paymentId) {
        TypedQuery<Payment> query = entityManager.createNamedQuery("findPaymentById", Payment.class);
        LOGGER.info("query with name [" + paymentId + "]");
        query.setParameter("id", paymentId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Long countPaymentsByLabel(long labelId) {
        TypedQuery<Long> query = entityManager.createNamedQuery("countPaymentsByLabel", Long.class);
        LOGGER.info("query with id [" + labelId + "]");
        query.setParameter("labelId", labelId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }

    @Override
    public void updatePayment(Payment payment) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(payment);
        transaction.commit();
    }
}
