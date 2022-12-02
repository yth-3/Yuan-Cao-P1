package org.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.revature.p1.dtos.requests.TicketCreationRequest;
import org.revature.p1.dtos.responses.Principal;
import org.revature.p1.services.TicketService;
import org.revature.p1.services.TokenService;
import org.revature.p1.utils.enums.ClientUserType;

public class CreateTicketHandler {
    private ObjectMapper mapper;
    private TicketService ticketService;
    private TokenService tokenService;

    public CreateTicketHandler(ObjectMapper mapper, TicketService ticketService, TokenService tokenService) {
        this.mapper = mapper;
        this.ticketService = ticketService;
        this.tokenService = tokenService;
    }


    public void createTicket(Context ctx) {
        try {
            String token = ctx.req.getHeader("Authorization");
            Principal principal = tokenService.extractPrincipal(token);
            if (principal == null
                    || principal.getType() == ClientUserType.ADMIN
                    || principal.getType() == ClientUserType.MANAGER) {
                ctx.status(401);
                return;
            }

            TicketCreationRequest req = mapper.readValue(ctx.req.getInputStream(), TicketCreationRequest.class);

            Double.valueOf("a");

            ticketService.createTicket(req, principal.getId());
            ctx.status(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
