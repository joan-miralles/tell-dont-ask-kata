package it.gabrieletondi.telldontaskkata.domain;

public class OrderMother {
    public static Order initialOrder() {
        return new Order(1);
    }

    public static Order initialApprovedOrder() {
        Order order = initialOrder();
        order.approve();
        return order;
    }

    public static Order initialRejectedOrder() {
        Order order = initialOrder();
        order.reject();
        return order;
    }

    public static Order initialShippedOrder() {
        Order order = initialOrder();
        order.ship();
        return order;
    }
}
