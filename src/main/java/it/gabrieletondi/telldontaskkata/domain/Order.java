package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public static Order createOrder() {
        Order order = new Order();
        order.status = OrderStatus.CREATED;
        order.items = new ArrayList<>();
        order.currency = "EUR";
        order.total = new BigDecimal("0.00");
        order.tax = new BigDecimal("0.00");
        return order;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addItem(OrderItem orderItem) {
        this.getItems().add(orderItem);
        this.total = this.total.add(orderItem.getTaxedAmount());
        this.tax = this.tax.add(orderItem.getTax());
    }
}
