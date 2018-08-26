package banking.api.controller;

import banking.api.dto.request.CreateProfileRequest;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/profiles")
@Produces(MediaType.APPLICATION_JSON)
public interface ProfileResource {

    @GET
    @Path("/{id}")
    Response getProfileById(@PathParam("id") Long id);

    @GET
    @Path("")
    Response getProfileByMobile(
            @QueryParam("code") String code,
            @QueryParam("number") String number);

    @POST
    @Path("/")
    Response createProfile(@Valid CreateProfileRequest createProfileRequest);
}
