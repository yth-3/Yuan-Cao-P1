package org.revature.p1.dtos.requests;

import org.revature.p1.utils.enums.TicketType;

public class TicketCreationRequest {
    private String amount;
    private String description;
    private String type;

    public TicketCreationRequest() {
        super();
    }

    public TicketCreationRequest(String amount, String description, String type) {
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TicketCreationRequest{" +
                "amount='" + amount + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
