package be.pxl.student.dao;

import be.pxl.student.entity.Payment;

import java.util.List;

public interface PaymentDao {
    Long countPaymentsByLabel(long labelId);
    // TODO more methods?
}
