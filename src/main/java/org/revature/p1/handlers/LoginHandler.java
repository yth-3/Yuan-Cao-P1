package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.LoginRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.services.TokenService;
import org.revature.p1.services.UserService;
import org.revature.p1.utils.exceptions.InvalidLoginException;

import java.io.IOException;

public class LoginHandler {
    private final ObjectMapper mapper;
    private final UserService userService;
    private final TokenService tokenService;

    public LoginHandler(ObjectMapper mapper, UserService userService, TokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public void loginUser(Context ctx) throws IOException {
        LoginRequest req = mapper.readValue(ctx.req.getInputStream(), LoginRequest.class);

        Principal principal;
        try {
            principal = userService.loginUser(req);
        } catch (InvalidLoginException e) {
            ctx.status(401);
            return;
        }

        String authorizationToken = tokenService.generateToken(principal);
        ctx.res.setHeader("authorization", authorizationToken);
        ctx.status(200);
    }
}
