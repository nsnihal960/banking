package banking.api.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import banking.api.controller.ProfileResource;
import banking.api.dto.request.CreateProfileRequest;
import banking.api.dto.response.Profile;
import banking.consumer.ProfileConsumer;

@Singleton
public class ProfileResourceImpl implements ProfileResource {
    private final ProfileConsumer profileConsumer;

    @Inject
    public ProfileResourceImpl(ProfileConsumer profileConsumer) {
        this.profileConsumer = profileConsumer;
    }

    @Override
    public Response getProfileById(String id) {
        return Response.status(200)
                .entity(profileConsumer.getUserById(id))
                .build();
    }

    @Override
    public Response getProfileByMobile(String mobile) {
        return Response.status(200)
                .entity(profileConsumer.getUserByMobile(mobile))
                .build();
    }

    @Override
    public Response getProfileByEmail(String email) {
        return Response.status(200)
                .entity(profileConsumer.getUserByEmail(email))
                .build();
    }

    @Override
    public Response createProfile(CreateProfileRequest request) {
        Profile profile = profileConsumer.createUser(request.profile);
        return Response.status(201)
                .entity(profile)
                .build();
    }
}
