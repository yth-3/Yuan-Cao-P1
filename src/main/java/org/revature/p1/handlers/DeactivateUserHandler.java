package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.AccountActivationRequest;
import org.revature.p1.dtos.requests.AccountDeactivationRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.services.TokenService;
import org.revature.p1.services.UserService;
import org.revature.p1.utils.enums.ClientUserType;

import java.io.IOException;

public class DeactivateUserHandler {
    private ObjectMapper mapper;
    private UserService userService;
    private TokenService tokenService;

    public DeactivateUserHandler(ObjectMapper mapper, UserService userService, TokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.tokenService = tokenService;
    }


    public void deactivateUser(Context ctx) throws IOException {
        try {
            String token = ctx.req.getHeader("Authorization");
            Principal principal = tokenService.extractPrincipal(token);
            if (principal.getType() == ClientUserType.EMPLOYEE || principal.getType() == ClientUserType.MANAGER) {
                ctx.status(401);
                return;
            }

            AccountDeactivationRequest req = mapper.readValue(ctx.req.getInputStream(), AccountDeactivationRequest.class);
            userService.deactivateUser(req);
            ctx.status(200);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
