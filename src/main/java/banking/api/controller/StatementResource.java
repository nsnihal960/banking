package banking.api.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/statement")
@Produces(MediaType.APPLICATION_JSON)
public interface StatementResource {
    @GET
    @Path("/{id}")
    public Response getStatement(@PathParam("id")String id);
}
