package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;

public class OrderApprovalUseCase {
    private final OrderRepository orderRepository;

    public OrderApprovalUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(OrderApprovalRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());
        checkOrderApprovalRules(request, order);
        if (request.isApproved()) {
            order.approve();
        } else {
            order.reject();
        }
        orderRepository.save(order);
    }

    private void checkOrderApprovalRules(OrderApprovalRequest request, Order order) {
        if (order.isShipped()) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (request.isApproved() && order.isRejected()) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (!request.isApproved() && order.isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }
    }

}
