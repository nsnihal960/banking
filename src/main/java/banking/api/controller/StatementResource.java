package banking.api.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/statement")
@Produces(MediaType.APPLICATION_JSON)
public interface StatementResource {
    @GET
    @Path("/name")
    public String getName();
}
