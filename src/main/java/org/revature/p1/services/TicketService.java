package org.revature.p1.services;

import org.revature.p1.daos.TicketDao;
import org.revature.p1.dtos.requests.TicketCreationRequest;
import org.revature.p1.models.Ticket;
import org.revature.p1.utils.enums.TicketType;

import java.sql.SQLException;

public class TicketService {
    private TicketDao ticketDao;

    public TicketService(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void createTicket(TicketCreationRequest req, String id) throws SQLException {
        Ticket ticket = new Ticket(id, req.getAmount(), req.getDescription(), TicketType.valueOf(req.getType()));
        // Fill in info from req and id
        this.ticketDao.create(ticket);
    }
}
