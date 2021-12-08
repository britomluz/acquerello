package com.restaurant.acquerello.dtos;

import com.restaurant.acquerello.models.SectorTables;
import com.restaurant.acquerello.models.TableState;

import java.time.LocalDate;

public class BookingCreateDTO {
    private LocalDate date;
    private Integer bookingHour;
    private Integer endBooking;
    private SectorTables sector;
    private Integer table;
    private Integer quantity;

    public BookingCreateDTO() {}

    public BookingCreateDTO(LocalDate date, Integer bookingHour, Integer endBooking, SectorTables sector, Integer table, Integer quantity) {
        this.date = date;
        this.bookingHour = bookingHour;
        this.endBooking = endBooking;
        this.sector = sector;
        this.table = table;
        this.quantity = quantity;
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
}
