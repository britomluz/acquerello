package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Booking;
import com.restaurant.acquerello.models.SectorTables;
import com.restaurant.acquerello.models.TableState;

import java.time.LocalDate;

public class BookingDTO {
    private Long id;
    private LocalDate date;
    private Integer bookingHour;
    private Integer endBooking;
    private SectorTables sector;
    private Integer table;
    private Integer quantity;
    private TableState state;

    public BookingDTO() {}

    public  BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.date = booking.getDate();
        this.bookingHour = booking.getBookingHour();
        this.endBooking = booking.getEndBooking();
        this.sector = booking.getSector();
        this.table = booking.getTable();
        this.quantity = booking.getQuantity();
        this.state = booking.getState();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
