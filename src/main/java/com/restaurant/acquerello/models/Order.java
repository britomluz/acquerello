package com.restaurant.acquerello.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders_list")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(strategy =  "native", name = "native")
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime aceptedDate;
    private OrderState state;
    private Double total;
    private OrderType type;
    private Integer tableNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_oder")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetails = new ArrayList<>();


    @OneToOne(mappedBy = "order")
    private Address address;

    public Order() {}

    public Order(LocalDateTime creationDate, LocalDateTime aceptedDate, OrderState state, Double total, OrderType type, Integer tableNumber) {
        this.creationDate = creationDate;
        this.aceptedDate = aceptedDate;
        this.state = state;
        this.total = total;
        this.type = type;
        this.tableNumber = tableNumber;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getAceptedDate() {
        return aceptedDate;
    }

    public void setAceptedDate(LocalDateTime aceptedDate) {
        this.aceptedDate = aceptedDate;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void addOrderDetail(OrderDetails orderDetail) {
        orderDetail.setOrder(this);
        orderDetails.add(orderDetail);
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addAddress(Address address) {
        address.setOrder(this);
    }



    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("id=").append(id);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", aceptedDate=").append(aceptedDate);
        sb.append(", state=").append(state);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
