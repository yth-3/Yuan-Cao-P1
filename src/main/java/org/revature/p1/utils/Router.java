package org.revature.p1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.revature.p1.daos.UserDao;
import org.revature.p1.handlers.LoginHandler;
import org.revature.p1.handlers.RegistrationHandler;
import org.revature.p1.services.TokenService;
import org.revature.p1.services.UserService;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {

    public static void route(Javalin app) {
        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);

        // Set Up UserService
        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);


        // Login
        LoginHandler loginHandler = new LoginHandler(mapper, userService, tokenService);

        // Registration
        RegistrationHandler registrationHandler = new RegistrationHandler(mapper, userService);

        app.routes(() -> {
            path("/auth", () -> {
                // Login
                get(loginHandler::loginUser);
            });

            path("/users", () -> {
                // Registration
                post(registrationHandler::registerUser);
            });

        });

    }
}
