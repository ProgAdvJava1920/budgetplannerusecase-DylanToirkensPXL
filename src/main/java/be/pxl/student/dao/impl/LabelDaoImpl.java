package be.pxl.student.dao.impl;

import be.pxl.student.dao.AccountDao;
import be.pxl.student.dao.LabelDao;
import be.pxl.student.entity.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;

public class LabelDaoImpl implements LabelDao {
    private static final Logger LOGGER = LogManager.getLogger(AccountDao.class);

    private EntityManager entityManager;

    public LabelDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Label findLabelById(long labelId) {
        // TODO
        return null;
    }

    @Override
    public Label findLabelByName(String name) {
        // TODO
        return null;
    }

    @Override
    public void removeLabel(Label label) {
        // TODO
    }

    @Override
    public void saveLabel(Label label) {
        // TODO
    }
}
