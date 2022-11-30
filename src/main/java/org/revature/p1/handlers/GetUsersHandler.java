package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.daos.UserDao;
import org.revature.p1.dtos.requests.LoginRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.models.User;
import org.revature.p1.services.TokenService;
import org.revature.p1.services.UserService;
import org.revature.p1.utils.enums.ClientUserType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetUsersHandler {
    private ObjectMapper mapper;
    private UserService userService;
    private TokenService tokenService;

    public GetUsersHandler(ObjectMapper mapper, UserService userService, TokenService tokenService) {
        this.mapper = mapper;
        this.userService = userService;
        this.tokenService = tokenService;
    }


    public void getUsers(Context ctx) throws IOException {
        try {
            String token = ctx.req.getHeader("Authorization");
            Principal principal = tokenService.extractPrincipal(token);
            if (principal.getType() == ClientUserType.EMPLOYEE || principal.getType() == ClientUserType.MANAGER) {
                ctx.status(401);
                return;
            }

            List<User> users = userService.getAllUsers();
            List<Principal> principals = new ArrayList<>();
            for (User user : users) {
                principals.add(new Principal(user));
            }
            ctx.status(200);
            ctx.json(principals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
