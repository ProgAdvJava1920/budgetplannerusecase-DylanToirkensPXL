package be.pxl.student.service;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.dao.impl.LabelDaoImpl;
import be.pxl.student.dao.impl.PaymentDaoImpl;
import be.pxl.student.entity.Label;
import be.pxl.student.util.EntityManagerUtil;
import be.pxl.student.util.exception.DuplicateLabelException;
import be.pxl.student.util.exception.LabelInUseException;
import be.pxl.student.util.exception.LabelNotFoundException;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class LabelService {
    private LabelDao labelDao;
    private PaymentDao paymentDao;

    public LabelService() {
        labelDao = new LabelDaoImpl(EntityManagerUtil.createEntityManager());
        paymentDao = new PaymentDaoImpl(EntityManagerUtil.createEntityManager());
    }
    // TODO more methods?

    public List<Label> findAllLabels() {
        return labelDao.findAllLabels();
    }

    public void addLabel(String name) throws DuplicateLabelException {
        Label existingLabel = labelDao.findLabelByName(name);
        if (existingLabel != null) {
            throw new DuplicateLabelException("There already exists a label with name [" + name + "]");
        }
        labelDao.saveLabel(new Label(name));
    }

    public void removeLabel(long labelId) throws LabelNotFoundException, LabelInUseException {
        Label label = labelDao.findLabelById(labelId);
        if (label == null) {
            throw new LabelNotFoundException("Label with id [" + labelId + "] cannot be found.");
        }
        Long numberOfPayments = paymentDao.countPaymentsByLabel(labelId);
        if (numberOfPayments > 0) {
            throw new LabelInUseException("Label [" + label.getName() + "] is in use. Remove the payments first or change their label." );
        }
        labelDao.removeLabel(label);
    }
}
