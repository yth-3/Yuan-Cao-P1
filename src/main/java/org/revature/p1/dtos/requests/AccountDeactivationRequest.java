package org.revature.p1.dtos.requests;

public class AccountDeactivationRequest {
    private String id;

    public AccountDeactivationRequest() {
        super();
    }

    public AccountDeactivationRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AccountDeactivationRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}
