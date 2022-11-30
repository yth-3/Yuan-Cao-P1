package org.revature.p1.dtos.responses;

import org.revature.p1.models.User;
import org.revature.p1.utils.enums.ClientUserType;

public class Principal {
    private String id;
    private String forename;
    private ClientUserType type;
    private Boolean active;
    private long issue;
    private long expiration;

    public Principal() {
        super();
    }

    public Principal(String id, String forename, ClientUserType type, Boolean active) {
        this.id = id;
        this.forename = forename;
        this.type = type;
        this.active = active;
    }

    public Principal(String id, String forename, ClientUserType type, Boolean active, long issue, long expiration) {
        this.id = id;
        this.forename = forename;
        this.type = type;
        this.active = active;
        this.issue = issue;
        this.expiration = expiration;
    }

    public Principal(User user) {
        this.id = user.getId();
        this.forename = user.getForename();
        this.type = user.getRole();
        this.active = user.isActive();
        this.issue = 0;
        this.expiration = 0;
    }

    public String getId() {
        return id;
    }

    public String getForename() {
        return forename;
    }

    public ClientUserType getType() {
        return type;
    }

    public Boolean getActive() {
        return active;
    }

    public long getIssue() {
        return issue;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public void setType(ClientUserType type) {
        this.type = type;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setIssue(long issue) {
        this.issue = issue;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", forename='" + forename + '\'' +
                ", type=" + type +
                ", active=" + active +
                ", issue=" + issue +
                ", expiration=" + expiration +
                '}';
    }
}

