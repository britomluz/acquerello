package com.restaurant.acquerello.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private Double total;

    @OneToMany(mappedBy = "orderDetails")
    private List<Order> orders = new ArrayList<>();

    public OrderDetails() {}

    public OrderDetails(String name, Integer quantity, Double price, Double total) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrders(Order order) {
        order.setOrderDetails(this);
        orders.add(order);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrderDetails{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
