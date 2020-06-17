package be.pxl.student.dao.impl;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.LabelDao;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class LabelDaoImpl implements LabelDao {
    private static final Logger LOGGER = LogManager.getLogger(AccountDao.class);

    private EntityManager entityManager;

    public LabelDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Label> findAllLabels() {
        TypedQuery<Label> query = entityManager.createNamedQuery("findAllLabels", Label.class);
        return query.getResultList();
    }

    @Override
    public Label findLabelById(long labelId) {
        TypedQuery<Label> query = entityManager.createNamedQuery("findLabelById", Label.class);
        LOGGER.info("query with name [" + labelId + "]");
        query.setParameter("id", labelId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Label findLabelByName(String name) {
        TypedQuery<Label> query = entityManager.createNamedQuery("findLabelByName", Label.class);
        LOGGER.info("query with name [" + name + "]");
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void removeLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(label);
        transaction.commit();
    }

    @Override
    public void saveLabel(Label label) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(label);
        transaction.commit();
    }
}
