package be.pxl.student.rest;

import be.pxl.student.util.exception.AccountAlreadyExistsException;
import be.pxl.student.util.exception.AccountNotFoundException;
import be.pxl.student.entity.Payment;
import be.pxl.student.rest.resources.AccountCreateResource;
import be.pxl.student.rest.resources.PaymentCreateResource;
import be.pxl.student.rest.resources.PaymentResource;
import be.pxl.student.service.AccountService;

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
    public Response getPayments(@PathParam("name") String name) {
        try {
            List<Payment> payments = accountService.findPaymentsByAccountName(name);
            return Response.ok(mapPayments(payments)).build();
        } catch (AccountNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{name}")
    public Response addPayment(@PathParam("name") String name, PaymentCreateResource paymentCreateResource) {
        try {
            accountService.addPayment(name, paymentCreateResource.getCounterAccount(), paymentCreateResource.getAmount(), paymentCreateResource.getDetail());
            return Response.created(UriBuilder.fromPath("/accounts/" + name).build()).build();
        } catch (AccountNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    private List<PaymentResource> mapPayments(List<Payment> payments) {
        return payments.stream().map(p -> mapPayment(p)).collect(Collectors.toList());
    }

    private PaymentResource mapPayment(Payment payment) {
        PaymentResource result = new PaymentResource();
        result.setId(payment.getId());
        result.setAmount(payment.getAmount());
        result.setCounterAccount(payment.getCounterAccount().getIBAN());
        result.setCurrency(payment.getCurrency());
        result.setDetail(payment.getDetail());
        return result;
    }
}
