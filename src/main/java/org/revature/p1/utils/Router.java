package org.revature.p1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.revature.p1.daos.TicketDao;
import org.revature.p1.daos.UserDao;
import org.revature.p1.handlers.*;
import org.revature.p1.services.TicketService;
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

        // Set Up TicketService
        TicketDao ticketDao = new TicketDao();
        TicketService ticketService = new TicketService(ticketDao);

        // Login
        LoginHandler loginHandler = new LoginHandler(mapper, userService, tokenService);

        // Registration
        RegistrationHandler registrationHandler = new RegistrationHandler(mapper, userService);

        // Get Users
        GetUsersHandler getUsersHandler = new GetUsersHandler(mapper, userService, tokenService);

        // Activate/deactivate user
        ActivateUserHandler activateUserHandler = new ActivateUserHandler(mapper, userService, tokenService);
        DeactivateUserHandler deactivateUserHandler = new DeactivateUserHandler(mapper, userService, tokenService);

        // User Ticket Management
        CreateTicketHandler createTicketHandler = new CreateTicketHandler(mapper, ticketService, tokenService);

        // Manager Ticket Managment
        GetPendingTicketsHandler getPendingTickets = new GetPendingTicketsHandler(mapper, ticketService, tokenService);

        app.routes(() -> {
            path("/auth", () -> {
                // Login
                get(loginHandler::loginUser);
            });

            path("/users", () -> {
                // Registration
                post(registrationHandler::registerUser);

                // Fetch List
                get(getUsersHandler::getUsers);

                // Activate/Deactivate Account
                put("/activate", activateUserHandler::activateUser);
                put("/deactivate", deactivateUserHandler::deactivateUser);
            });

            path("/tickets", () -> {
                // Employee Ticket Management
                post(createTicketHandler::createTicket);

                // Manager Ticket Management
                get("/manager/pending", getPendingTicketsHandler::getPendingTickets);
            });
        });

    }
}
