package banking.api.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.api.dto.request.GetStatementRequest;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
public interface AccountResource {
    @GET
    @Path("/statement")
    Response getStatement(
            @QueryParam("id") Long id,
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime,
            @QueryParam("count") Integer count);

    @GET
    @Path("/balance/{id}")
    Response getBalance(@NotNull @PathParam("id") Long id);
}
