package org.revature.p1.dtos.requests;

import org.revature.p1.utils.enums.TicketStatus;

public class TicketsByStatusRequest {
    TicketStatus status;

    public TicketsByStatusRequest() {
        super();
    }

    public TicketsByStatusRequest(TicketStatus status) {
        this.status = status;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TicketsByStatusRequest{" +
                "status=" + status +
                '}';
    }
}
