package be.pxl.student.rest;

import be.pxl.student.entity.Payment;
import be.pxl.student.rest.resources.AccountCreateResource;
import be.pxl.student.rest.resources.AccountPaymentsResource;
import be.pxl.student.rest.resources.PaymentCreateResource;
import be.pxl.student.rest.resources.PaymentResource;
import be.pxl.student.service.AccountService;
import be.pxl.student.util.exception.AccountAlreadyExistsException;
import be.pxl.student.util.exception.AccountNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.List;
import java.util.stream.Collectors;

@Path("/accounts")
public class AccountsRest {
    @Inject
    private AccountService accountService;

    @POST
    public Response addAccount(AccountCreateResource accountCreateResource) {
        try {
            accountService.addAccount(accountCreateResource.getName(), accountCreateResource.getIban());
            return Response.created(UriBuilder.fromPath("/accounts/" + accountCreateResource.getName()).build()).build();
        } catch (AccountAlreadyExistsException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayments(@PathParam("name") String name, @QueryParam("label") String label) {
        try {
            List<Payment> payments = accountService.findPaymentsByAccountName(name);
            return Response.ok(mapToAccountPaymentResource(payments, label)).build();
        } catch (AccountNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{name}")
    public Response addPayment(@PathParam("name") String name, PaymentCreateResource paymentCreateResource) {
        try {
            accountService.addPayment(name, paymentCreateResource.getCounterAccount(), paymentCreateResource.getAmount(), paymentCreateResource.getDate(), paymentCreateResource.getDetail());
            return Response.created(UriBuilder.fromPath("/accounts/" + name).build()).build();
        } catch (AccountNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    private AccountPaymentsResource mapToAccountPaymentResource(List<Payment> payments, String labelFilter) {
        AccountPaymentsResource result = new AccountPaymentsResource();
        List<PaymentResource> paymentResources = mapPayments(payments, labelFilter);
        float receivingAmount = (float)paymentResources.stream().filter(p -> p.getAmount() > 0).mapToDouble(PaymentResource::getAmount).sum();
        float spendingAmount = (float)paymentResources.stream().filter(p -> p.getAmount() < 0).mapToDouble(PaymentResource::getAmount).sum();
        float resultAmount = receivingAmount + spendingAmount;

        result.setPayments(paymentResources);
        result.setReceivingAmount(receivingAmount);
        result.setSpendingAmount(spendingAmount);
        result.setResultAmount(resultAmount);
        return result;
    }

    private List<PaymentResource> mapPayments(List<Payment> payments, String labelFilter) {
        if (labelFilter == null) {
            return payments.stream().map(p -> mapPayment(p)).collect(Collectors.toList());
        } else {
            return payments.stream()
                    .filter(p -> p.getLabel() != null)
                    .filter(p -> p.getLabel().getName().equals(labelFilter))
                    .map(p -> mapPayment(p))
                    .collect(Collectors.toList());
        }
    }

    private PaymentResource mapPayment(Payment payment) {
        PaymentResource result = new PaymentResource();
        result.setId(payment.getId());
        result.setAmount(payment.getAmount());
        result.setCounterAccount(payment.getCounterAccount().getIBAN());
        result.setCurrency(payment.getCurrency());
        result.setDate(payment.getDate().toLocalDate());
        result.setDetail(payment.getDetail());
        result.setLabel(payment.getLabel());
        return result;
    }
}
