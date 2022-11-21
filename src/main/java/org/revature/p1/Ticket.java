package org.revature.p1;

public class Ticket {
    private TicketStatus status;
    private String description;
    private float amount;

    public Ticket(TicketStatus status, String description, float amount) throws TicketInvalidAmountException {
        if (amount < 0) {
            throw new TicketInvalidAmountException("test");
        }

        this.status = status;
        this.description = description;
        this.amount = amount;
    }

    public Ticket() throws TicketInvalidAmountException {
        this(TicketStatus.PENDING, "", 0.0f);
    }
}
