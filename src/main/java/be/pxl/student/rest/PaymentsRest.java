package be.pxl.student.rest;

import be.pxl.student.service.PaymentService;
import be.pxl.student.util.exception.LabelNotFoundException;
import be.pxl.student.util.exception.PaymentNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/payments")
public class PaymentsRest {
    @Inject
    private PaymentService paymentService;

    @POST
    @Path("{paymentId}/link/{labelId}")
    public Response linkPaymentToLabel(@PathParam("paymentId") long paymentId, @PathParam("labelId") long labelId) {
        try {
            paymentService.linkPaymentToLabel(paymentId, labelId);
            return Response.ok().build();
        } catch (PaymentNotFoundException | LabelNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{paymentId}")
    public Response removePayment(@PathParam("paymentId") long paymentId) {
        try {
            paymentService.removePayment(paymentId);
            return Response.accepted().build();
        } catch (PaymentNotFoundException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
        }
    }
}
