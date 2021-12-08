package com.restaurant.acquerello.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private LocalDate dateBooking;
    private Integer bookingHour;
    private Integer endBooking;
    private SectorTables sector;
    private Integer tableNumber;
    private Integer quantity;
    private TableState state;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Booking() {}

    public Booking(LocalDate date, Integer bookingHour, Integer endBooking, SectorTables sector, Integer table, Integer quantity, TableState state) {
        this.dateBooking = date;
        this.bookingHour = bookingHour;
        this.endBooking = endBooking;
        this.sector = sector;
        this.tableNumber = table;
        this.quantity = quantity;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return dateBooking;
    }

    public void setDate(LocalDate date) {
        this.dateBooking = date;
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
        return tableNumber;
    }

    public void setTable(Integer table) {
        this.tableNumber = table;
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

    public LocalDate getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(LocalDate dateBooking) {
        this.dateBooking = dateBooking;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Booking{");
        sb.append("id=").append(id);
        sb.append(", date=").append(dateBooking);
        sb.append(", bookingHour=").append(bookingHour);
        sb.append(", endBooking=").append(endBooking);
        sb.append(", sector=").append(sector);
        sb.append(", table=").append(tableNumber);
        sb.append(", quantity=").append(quantity);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }
}
