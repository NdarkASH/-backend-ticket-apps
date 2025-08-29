package com.darknash.tickets.exceptions;

public class TicketTypeNotFoundException extends RuntimeException {
    public TicketTypeNotFoundException(String message) {
        super(message);
    }
}
