package banking.api.controller;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.api.dto.request.GetStatementRequest;

@Path("/statement")
@Produces(MediaType.APPLICATION_JSON)
public interface StatementResource {
    @POST
    @Path("")
    public Response getStatement(@Valid GetStatementRequest getStatementRequest);
}
