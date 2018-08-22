package banking.api.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.api.dto.request.CreateProfileRequest;

@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
public interface ProfileResource {

    @GET
    @Path("/id/{id}")
    Response getProfileById(@PathParam("id") String id);

    @GET
    @Path("/mobile/{mobile}")
    Response getProfileByMobile(@PathParam("mobile") String mobile);

    @GET
    @Path("/email/{email}")
    Response getProfileByEmail(@PathParam("email") String email);

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createProfile(CreateProfileRequest createProfileRequest);
}
