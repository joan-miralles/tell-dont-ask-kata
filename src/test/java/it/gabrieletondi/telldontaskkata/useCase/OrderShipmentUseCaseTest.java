package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.doubles.TestShipmentService;
import org.junit.Test;

import static it.gabrieletondi.telldontaskkata.domain.OrderMother.initialApprovedOrder;
import static it.gabrieletondi.telldontaskkata.domain.OrderMother.initialOrder;
import static it.gabrieletondi.telldontaskkata.domain.OrderMother.initialRejectedOrder;
import static it.gabrieletondi.telldontaskkata.domain.OrderMother.initialShippedOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OrderShipmentUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private final TestShipmentService shipmentService = new TestShipmentService();
    private final OrderShipmentUseCase useCase = new OrderShipmentUseCase(orderRepository, shipmentService);

    @Test
    public void shipApprovedOrder() throws Exception {
        Order approvedOrder = initialApprovedOrder();
        orderRepository.addOrder(approvedOrder);

        useCase.run(new OrderShipmentRequest(1));

        assertTrue(orderRepository.getSavedOrder().isShipped());
        assertThat(shipmentService.getShippedOrder(), is(approvedOrder));
    }

    @Test(expected = OrderCannotBeShippedException.class)
    public void createdOrdersCannotBeShipped() throws Exception {
        orderRepository.addOrder(initialOrder());

        useCase.run(new OrderShipmentRequest(1));

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }

    @Test(expected = OrderCannotBeShippedException.class)
    public void rejectedOrdersCannotBeShipped() throws Exception {
        orderRepository.addOrder(initialRejectedOrder());

        useCase.run(new OrderShipmentRequest(1));

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }

    @Test(expected = OrderCannotBeShippedTwiceException.class)
    public void shippedOrdersCannotBeShippedAgain() throws Exception {
        orderRepository.addOrder(initialShippedOrder());

        useCase.run(new OrderShipmentRequest(1));

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
        assertThat(shipmentService.getShippedOrder(), is(nullValue()));
    }
}
