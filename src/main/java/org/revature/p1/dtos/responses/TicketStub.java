package org.revature.p1.dtos.responses;

import org.revature.p1.models.User;
import org.revature.p1.utils.enums.TicketStatus;
import org.revature.p1.utils.enums.TicketType;

public class TicketStub {
    private String id;
    private User issuer;
    private long submitDate;
    private User resolver;
    private long resolveDate;
    private String description;
    private double amount;
    private byte [] receipt;
    private String paymentId;
    private TicketStatus status;
    private TicketType type;

    public TicketStub() {
        super();
    }

    public TicketStub(String id,
                      User issuer,
                      long submitDate,
                      User resolver,
                      long resolveDate,
                      String description,
                      double amount,
                      byte[] receipt,
                      String paymentId,
                      TicketStatus status,
                      TicketType type) {
        this.id = id;
        this.issuer = issuer;
        this.submitDate = submitDate;
        this.resolver = resolver;
        this.resolveDate = resolveDate;
        this.description = description;
        this.amount = amount;
        this.receipt = receipt;
        this.paymentId = paymentId;
        this.status = status;
        this.type = type;
    }
}
