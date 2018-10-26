package application.service;

public class TicketContent {
    public static void main(String[] args) {
        TicketProvider ticketProvider = new TicketProvider();
        Thread t1 = new Thread(ticketProvider,"夏晨航");
        t1.start();
        Thread t2 = new Thread(ticketProvider,"夏东宇");
        t2.start();

    }
}
