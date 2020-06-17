package be.pxl.student.dao;

import be.pxl.student.entity.Payment;

public interface PaymentDao {
    Payment findPaymentById(long paymentId);
    Long countPaymentsByLabel(long labelId);
    void updatePayment(Payment payment);
}
