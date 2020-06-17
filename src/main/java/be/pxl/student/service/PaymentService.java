package be.pxl.student.service;

import be.pxl.student.dao.LabelDao;
import be.pxl.student.dao.PaymentDao;
import be.pxl.student.dao.impl.LabelDaoImpl;
import be.pxl.student.dao.impl.PaymentDaoImpl;
import be.pxl.student.entity.Label;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.EntityManagerUtil;
import be.pxl.student.util.exception.LabelNotFoundException;
import be.pxl.student.util.exception.PaymentNotFoundException;

import javax.ejb.Stateless;

@Stateless
public class PaymentService {
    private PaymentDao paymentDao;
    private LabelDao labelDao;

    public PaymentService() {
        paymentDao = new PaymentDaoImpl(EntityManagerUtil.createEntityManager());
        labelDao = new LabelDaoImpl(EntityManagerUtil.createEntityManager());
    }

    public void linkPaymentToLabel(long paymentId, long labelId) throws PaymentNotFoundException, LabelNotFoundException {
        Payment payment = paymentDao.findPaymentById(paymentId);
        if (payment == null) {
            throw new PaymentNotFoundException("Payment with id [" + paymentId + "] cannot be found");
        }
        Label label = labelDao.findLabelById(labelId);
        if (label == null) {
            throw new LabelNotFoundException("Label with id [" + labelId + "] cannot be found.");
        }
        payment.setLabel(label);
        paymentDao.updatePayment(payment);
    }

    public void removePayment(long paymentId) throws PaymentNotFoundException {
        Payment payment = paymentDao.findPaymentById(paymentId);
        if (payment == null) {
            throw new PaymentNotFoundException("Payment with id [" + paymentId + "] cannot be found.");
        }
        paymentDao.removePayment(payment);
    }
}
