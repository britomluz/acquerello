package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.Booking;
import com.restaurant.acquerello.models.SectorTables;
import com.restaurant.acquerello.models.TableAvailability;
import com.restaurant.acquerello.models.TableState;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDTO {
    private Long id;
    private LocalDate dateBooking;
    private LocalTime bookingHour;
    private LocalTime endBooking;
    private SectorTables sector;
    private Integer tableNumber;
    private Integer quantity;
    private TableState state;
    private TableAvailability tableAvailability;

    public BookingDTO() {}

    public  BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.dateBooking = booking.getDate();
        this.bookingHour = booking.getBookingHour();
        this.endBooking = booking.getEndBooking();
        this.sector = booking.getSector();
        this.tableNumber = booking.getTable();
        this.quantity = booking.getQuantity();
        this.state = booking.getState();
        this.tableAvailability = booking.getAvailability();
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(LocalDate dateBooking) {
        this.dateBooking = dateBooking;
    }

    public LocalTime getBookingHour() {
        return bookingHour;
    }

    public void setBookingHour(LocalTime bookingHour) {
        this.bookingHour = bookingHour;
    }

    public LocalTime getEndBooking() {
        return endBooking;
    }

    public void setEndBooking(LocalTime endBooking) {
        this.endBooking = endBooking;
    }

    public SectorTables getSector() {
        return sector;
    }

    public void setSector(SectorTables sector) {
        this.sector = sector;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
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

    public TableAvailability getTableAvailability() {
        return tableAvailability;
    }

    public void setTableAvailability(TableAvailability tableAvailability) {
        this.tableAvailability = tableAvailability;
    }
}
