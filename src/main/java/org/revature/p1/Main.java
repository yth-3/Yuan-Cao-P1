package org.revature.p1;

import org.revature.p1.models.Ticket;
import org.revature.p1.utils.exceptions.TicketInvalidAmountException;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Ticket> tickets = new ArrayList<>();

        while(true) {
            System.out.println("Type stop to terminate the program; type size to see how many tickets have been entered.");
            System.out.println("Add another ticket? (type stop to terminate program)");
            Scanner scan = new Scanner(System.in);

            String line = scan.nextLine();
            switch(line) {
                case "size":
                    System.out.println("# of tickets entered: " + Integer.toString(tickets.size()));
                    break;
                case "stop":
                    System.exit(0);
                    break;
                default:
                    try {
                        Ticket ticket = new Ticket();
                        tickets.add(ticket);
                    } catch (TicketInvalidAmountException e) {
                        System.out.println("The reimbursement amount sought cannot be negative.");
                    }
            }
        }
    }
}