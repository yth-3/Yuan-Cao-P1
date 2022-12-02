package org.revature.p1.models;

import org.revature.p1.utils.enums.TicketStatus;
import org.revature.p1.utils.enums.TicketType;

import java.util.Arrays;

public class Ticket {
    private String submitterId;
    private String amount;
    private String description;
    private TicketType type;

    public Ticket() {
        super();
    }

    public Ticket(String submitterId, String amount, String description, TicketType type) {
        this.submitterId = submitterId;
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public String getSubmitterId() {
        return submitterId;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public TicketType getType() {
        return type;
    }

    public void setSubmitterId(String submitterId) {
        this.submitterId = submitterId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "submitterId='" + submitterId + '\'' +
                ", amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
