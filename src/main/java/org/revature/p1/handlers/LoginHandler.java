package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.LoginRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.services.TokenService;
import org.revature.p1.services.UserService;
import org.revature.p1.utils.exceptions.InvalidLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginHandler {
    private final ObjectMapper mapper;
    private final UserService userService;
    private final TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger("Login");

    public LoginHandler(ObjectMapper mapper, UserService userService, TokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public void loginUser(Context ctx) throws IOException {
        logger.info("Attempting to login a user...");
        LoginRequest req = mapper.readValue(ctx.req.getInputStream(), LoginRequest.class);

        Principal principal;
        try {
            logger.info("... logging in ...");
            principal = userService.loginUser(req);
        } catch (InvalidLoginException e) {
            ctx.status(401);
            ctx.result("Please make sure that you have a valid and active account, " +
                    "and that you entered your username/password correctly.");
            logger.info("... rejected.");
            return;
        }

        String authorizationToken = tokenService.generateToken(principal);
        ctx.res.setHeader("authorization", authorizationToken);
        ctx.status(200);
        logger.info("... accepted.");
    }
}
