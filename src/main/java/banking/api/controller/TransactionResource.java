package banking.api.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.api.dto.request.AddBalanceRequest;
import banking.api.dto.request.DeductBalanceRequest;
import banking.api.dto.request.TransferBalanceRequest;

@Path("/transaction")
public interface TransactionResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    Response addBalance(AddBalanceRequest addBalanceRequest);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deduct")
    Response deductBalance(DeductBalanceRequest addBalanceRequest);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/transfer")
    Response transferBalance(TransferBalanceRequest addBalanceRequest);
}
