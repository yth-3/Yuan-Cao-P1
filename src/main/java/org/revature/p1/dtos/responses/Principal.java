package org.revature.p1.dtos.responses;

import org.revature.p1.utils.enums.ClientUserType;

public class Principal {
    private String id;
    private String forename;
    private ClientUserType type;
    private Boolean active;

    public Principal() {
        super();
    }

    public Principal(String id, String forename, ClientUserType type, Boolean active) {
        this.id = id;
        this.forename = forename;
        this.type = type;
        this.active = active;
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

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", forename='" + forename + '\'' +
                ", type=" + type +
                ", active=" + active +
                '}';
    }
}

