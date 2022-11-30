package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.RegistrationRequest;
import org.revature.p1.services.UserService;
import org.revature.p1.utils.exceptions.DuplicateUserException;
import org.revature.p1.utils.exceptions.InvalidLoginException;
import org.revature.p1.utils.exceptions.InvalidRegistrationException;

import java.io.IOException;

public class RegistrationHandler {
    private final ObjectMapper mapper;
    private final UserService userService;

    public RegistrationHandler(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    public void registerUser(Context ctx) throws IOException {
        RegistrationRequest req = mapper.readValue(ctx.req.getInputStream(), RegistrationRequest.class);
        try {
            userService.registerUser(req);
            ctx.status(201);
        } catch (InvalidRegistrationException e) {
            ctx.status(400); // Bad Request
        } catch (DuplicateUserException e) {
            ctx.status(409); // Conflict
        }
    }
}
