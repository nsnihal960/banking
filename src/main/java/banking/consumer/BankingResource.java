package banking.consumer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/banking")
@Produces(MediaType.APPLICATION_JSON)
public interface BankingResource {
    @GET
    @Path("/name")
    public String getName();
}
