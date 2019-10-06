package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.service.ShipmentService;

public class OrderShipmentUseCase {
    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;

    public OrderShipmentUseCase(OrderRepository orderRepository, ShipmentService shipmentService) {
        this.orderRepository = orderRepository;
        this.shipmentService = shipmentService;
    }

    public void run(OrderShipmentRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());

        checkOrderShipRules(order);

        shipmentService.ship(order);

        order.ship();
        orderRepository.save(order);
    }

    private void checkOrderShipRules(Order order) {
        if (order.isCreated() || order.isRejected()) {
            throw new OrderCannotBeShippedException();
        }

        if (order.isShipped()) {
            throw new OrderCannotBeShippedTwiceException();
        }
    }
}
