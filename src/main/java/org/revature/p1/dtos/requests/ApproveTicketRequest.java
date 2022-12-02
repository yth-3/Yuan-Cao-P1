package org.revature.p1.dtos.requests;

public class ApproveTicketRequest {
    private String ticketId;

    public ApproveTicketRequest() {
        super();
    }

    public ApproveTicketRequest(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "ApproveTicketRequest{" +
                "ticketId='" + ticketId + '\'' +
                '}';
    }
}
