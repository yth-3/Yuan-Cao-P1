package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.models.User;
import org.revature.p1.services.TokenService;
import org.revature.p1.services.UserService;
import org.revature.p1.utils.enums.ClientUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetUsersHandler {
    private ObjectMapper mapper;
    private UserService userService;
    private TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger("GetUsers");

    public GetUsersHandler(ObjectMapper mapper, UserService userService, TokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.tokenService = tokenService;
    }


    public void getUsers(Context ctx) throws IOException {
        logger.info("Attempting to query all users...");
        try {
            String token = ctx.req.getHeader("Authorization");
            Principal principal = tokenService.extractPrincipal(token);
            if (principal == null || principal.getType() != ClientUserType.ADMIN) {
                logger.warn("... rejected due to unauthorized access.");
                ctx.status(401);
                return;
            }

            List<User> users = userService.getAllUsers();
            List<Principal> principals = new ArrayList<>();
            for (User user : users) {
                principals.add(new Principal(user));
            }
            logger.info("... accepted.");
            ctx.status(200);
            ctx.json(principals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
