package com.restaurant.acquerello.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private LocalDate date;
    private Integer bookingHour;
    private Integer endBooking;
    private SectorTables sector;
    private Integer table;
    private Integer quantity;
    private TableState state;

    public Booking() {}

    public Booking(LocalDate date, Integer bookingHour, Integer endBooking, SectorTables sector, Integer table, Integer quantity, TableState state) {
        this.date = date;
        this.bookingHour = bookingHour;
        this.endBooking = endBooking;
        this. sector = sector;
        this.table = table;
        this.quantity = quantity;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getBookingHour() {
        return bookingHour;
    }

    public void setBookingHour(Integer bookingHour) {
        this.bookingHour = bookingHour;
    }

    public Integer getEndBooking() {
        return endBooking;
    }

    public void setEndBooking(Integer endBooking) {
        this.endBooking = endBooking;
    }

    public SectorTables getSector() {
        return sector;
    }

    public void setSector(SectorTables sector) {
        this.sector = sector;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public TableState getState() {
        return state;
    }

    public void setState(TableState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Booking{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", bookingHour=").append(bookingHour);
        sb.append(", endBooking=").append(endBooking);
        sb.append(", sector=").append(sector);
        sb.append(", table=").append(table);
        sb.append(", quantity=").append(quantity);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
