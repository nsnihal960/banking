package banking.api.controller;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import banking.api.dto.request.CreateProfileRequest;
import banking.api.dto.response.Mobile;

@Path("/profile")
@Produces(MediaType.APPLICATION_JSON)
public interface ProfileResource {

    @GET
    @Path("/id/{id}")
    Response getProfileById(@PathParam("id") Long id);

    @POST
    @Path("/mobile")
    Response getProfileByMobile(@Valid Mobile mobile);

    @POST
    @Path("/create")
    Response createProfile(@Valid CreateProfileRequest createProfileRequest);
}
