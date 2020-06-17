package be.pxl.student.rest.resources;

import java.util.List;

public class AccountPaymentsResource {
    private List<PaymentResource> payments;
    private float receivingAmount;
    private float spendingAmount;
    private float resultAmount;

    public List<PaymentResource> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResource> payments) {
        this.payments = payments;
    }

    public float getReceivingAmount() {
        return receivingAmount;
    }

    public void setReceivingAmount(float receivingAmount) {
        this.receivingAmount = receivingAmount;
    }

    public float getSpendingAmount() {
        return spendingAmount;
    }

    public void setSpendingAmount(float spendingAmount) {
        this.spendingAmount = spendingAmount;
    }

    public float getResultAmount() {
        return resultAmount;
    }

    public void setResultAmount(float resultAmount) {
        this.resultAmount = resultAmount;
    }
}
