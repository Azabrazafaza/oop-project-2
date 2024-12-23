package service;

import enums.TicketStatus;
import enums.UrgencyLevel;
import lombok.Getter;
import lombok.Setter;
import users.Employee;

import java.time.LocalDateTime;
@Getter
@Setter
public class Request {


    public boolean signed;
    public Employee sender;
    public Employee recipient;
    public String report;
    private LocalDateTime timestamp;
    private UrgencyLevel urgencyLevel;
    private TicketStatus status;

    public Request(Employee sender, Employee recipient, String report,  UrgencyLevel urgencyLevel) {
        this.sender = sender;
        this.recipient = recipient;
        this.report = report;
        this.timestamp = LocalDateTime.now();
        this.urgencyLevel = urgencyLevel;
        this.signed = false;
        this.status = TicketStatus.NOTANSWERED;
    }
    public void signRequest() {
        this.signed = true;
        System.out.println("Request signed by Dean");
    }

    public String formatRequest() {
        return "From: " + sender + "\nTo: " + recipient + "\nMessage: " + report + "\nSent: " + timestamp;
    }
    @Override
    public String toString(){
        return formatRequest();
    }

}
