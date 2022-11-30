package org.revature.p1.models;

import org.revature.p1.utils.enums.TicketStatus;
import org.revature.p1.utils.enums.TicketType;

import java.util.Arrays;

public class Ticket {
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

    public Ticket() {
        super();
    }

    public Ticket(String id,
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

    public String getId() {
        return id;
    }

    public User getIssuer() {
        return issuer;
    }

    public long getSubmitDate() {
        return submitDate;
    }

    public User getResolver() {
        return resolver;
    }

    public long getResolveDate() {
        return resolveDate;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public byte[] getReceipt() {
        return receipt;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public TicketType getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public void setSubmitDate(long submitDate) {
        this.submitDate = submitDate;
    }

    public void setResolver(User resolver) {
        this.resolver = resolver;
    }

    public void setResolveDate(long resolveDate) {
        this.resolveDate = resolveDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setReceipt(byte[] receipt) {
        this.receipt = receipt;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", issuer=" + issuer +
                ", submitDate=" + submitDate +
                ", resolver=" + resolver +
                ", resolveDate=" + resolveDate +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", receipt=" + Arrays.toString(receipt) +
                ", paymentId='" + paymentId + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
