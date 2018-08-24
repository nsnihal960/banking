package banking.api.controller.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import banking.api.controller.ProfileResource;
import banking.api.dto.request.CreateProfileRequest;
import banking.api.dto.response.Mobile;
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
    public Response getProfileById(Long id) {
        return Response.status(200)
                .entity(profileConsumer.getUserById(id))
                .build();
    }

    @Override
    public Response getProfileByMobile(String countryCode, String number) {
        Mobile mobile = new Mobile(countryCode, number);
        mobile.validate();
        return Response.status(200)
                .entity(profileConsumer.getUserByMobile(mobile))
                .build();
    }

    @Override
    public Response createProfile(@Valid CreateProfileRequest request) {
        request.validate();
        Profile profile = profileConsumer.createUser(request.profile);
        return Response.status(201)
                .entity(profile)
                .build();
    }
}
