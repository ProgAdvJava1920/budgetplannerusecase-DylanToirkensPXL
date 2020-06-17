package be.pxl.student.rest;

import be.pxl.student.service.PaymentService;
import be.pxl.student.util.exception.AccountAlreadyExistsException;
import be.pxl.student.util.exception.LabelNotFoundException;
import be.pxl.student.util.exception.PaymentNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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
}
