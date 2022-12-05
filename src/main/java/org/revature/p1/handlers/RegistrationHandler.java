package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.RegistrationRequest;
import org.revature.p1.services.UserService;
import org.revature.p1.utils.exceptions.DuplicateUserException;
import org.revature.p1.utils.exceptions.InvalidRegistrationException;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationHandler {
    private final ObjectMapper mapper;
    private final UserService userService;
    private final static Logger logger = LoggerFactory.getLogger("Registration");

    public RegistrationHandler(ObjectMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    public void registerUser(Context ctx) throws IOException {
        logger.info("Attempting to register a new user...");
        RegistrationRequest req = mapper.readValue(ctx.req.getInputStream(), RegistrationRequest.class);
        try {
            userService.registerUser(req);
            ctx.status(201);
            logger.info("... accepted.");
        } catch (InvalidRegistrationException e) {
            ctx.status(400); // Bad Request
            logger.info("... rejected for not filled out all fields: " + e.getMessage() + ".");
            ctx.result("Please correctly fill in all necessary fields: " + e.getMessage());
        } catch (DuplicateUserException e) {
            ctx.status(409); // Conflict
            ctx.result("Username already exists.");
            logger.info("... rejected, duplicate user.");
        }
    }
}
