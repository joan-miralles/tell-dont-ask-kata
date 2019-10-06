package it.gabrieletondi.telldontaskkata.domain;

public class OrderMother {
    public static Order initialOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setId(1);
        return order;
    }

    public static Order initialApprovedOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.APPROVED);
        order.setId(1);
        return order;
    }

    public static Order initialRejectedOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.REJECTED);
        order.setId(1);
        return order;
    }

    public static Order initialShippedOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.SHIPPED);
        order.setId(1);
        return order;
    }
}
