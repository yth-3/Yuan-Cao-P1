package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.AccountActivationRequest;
import org.revature.p1.dtos.requests.RegistrationRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.services.TokenService;
import org.revature.p1.services.UserService;
import org.revature.p1.utils.enums.ClientUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ActivateUserHandler {
    private ObjectMapper mapper;
    private UserService userService;
    private TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger("ActivateUsers");

    public ActivateUserHandler(ObjectMapper mapper, UserService userService, TokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.tokenService = tokenService;
    }


    public void activateUser(Context ctx) throws IOException {
        logger.info("Attempting to deactivate a user... ");
        try {
            String token = ctx.req.getHeader("Authorization");
            Principal principal = tokenService.extractPrincipal(token);
            if (principal == null
                    || principal.getType() == ClientUserType.EMPLOYEE
                    || principal.getType() == ClientUserType.MANAGER) {
                logger.warn("... rejected for insufficient privilege.");
                ctx.status(401);
                return;
            }

            AccountActivationRequest req = mapper.readValue(ctx.req.getInputStream(), AccountActivationRequest.class);
            userService.activateUser(req);
            logger.info("... accepted.");
            ctx.status(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
