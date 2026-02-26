package com.soltec.web.entity;

import java.io.Serializable;
import java.util.Objects;

public class TicketLineId implements Serializable {
    private String ticket;
    private int line;

    public TicketLineId() {}

    public TicketLineId(String ticket, int line) {
        this.ticket = ticket;
        this.line = line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketLineId that = (TicketLineId) o;
        return line == that.line && Objects.equals(ticket, that.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticket, line);
    }
}
