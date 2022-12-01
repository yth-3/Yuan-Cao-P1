package org.revature.p1.dtos.requests;

public class AccountActivationRequest {
    private String id;

    public AccountActivationRequest() {
        super();
    }

    public AccountActivationRequest(String id) {
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
        return "AccountActivationRequest{" +
                "id='" + id + '\'' +
                '}';
    }
}
