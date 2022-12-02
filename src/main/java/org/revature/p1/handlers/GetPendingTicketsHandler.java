package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.models.Ticket;
import org.revature.p1.models.TicketStub;
import org.revature.p1.services.TicketService;
import org.revature.p1.services.TokenService;
import org.revature.p1.utils.enums.ClientUserType;

import java.util.List;

public class GetPendingTicketsHandler {
    private ObjectMapper mapper;
    private TicketService ticketService;
    private TokenService tokenService;

    public GetPendingTicketsHandler(ObjectMapper mapper, TicketService ticketService, TokenService tokenService) {
        this.mapper = mapper;
        this.ticketService = ticketService;
        this.tokenService = tokenService;
    }


    public void getPendingTickets(Context ctx) {
        try {
            String token = ctx.req.getHeader("Authorization");
            Principal principal = tokenService.extractPrincipal(token);
            if (principal == null || principal.getType() == ClientUserType.ADMIN || principal.getType() == ClientUserType.EMPLOYEE) {
                ctx.status(401);
                return;
            }

            List<Ticket> ticketStubs = ticketService.getAllTickets();
            ctx.status(200);
            ctx.json(ticketStubs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
