package org.revature.p1;

import io.javalin.Javalin;
import org.revature.p1.utils.Router;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(c -> {
            c.contextPath = "/reimbursement_system";
        }).start(8080);

        Router.route(app);
    }
}