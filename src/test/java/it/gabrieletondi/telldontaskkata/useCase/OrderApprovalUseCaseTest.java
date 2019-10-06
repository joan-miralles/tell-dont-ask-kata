package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderMother;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import org.junit.Test;

import static it.gabrieletondi.telldontaskkata.domain.OrderApprovalRequestMother.approvedRequest;
import static it.gabrieletondi.telldontaskkata.domain.OrderApprovalRequestMother.rejectedRequest;
import static it.gabrieletondi.telldontaskkata.domain.OrderMother.initialOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OrderApprovalUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private final OrderApprovalUseCase orderApprovalUseCase = new OrderApprovalUseCase(orderRepository);

    @Test
    public void approvedExistingOrder() throws Exception {
        orderRepository.addOrder(initialOrder());

        orderApprovalUseCase.run(approvedRequest());

        final Order savedOrder = orderRepository.getSavedOrder();
        assertTrue(savedOrder.isApproved());
    }

    @Test
    public void rejectedExistingOrder() throws Exception {
        orderRepository.addOrder(initialOrder());

        orderApprovalUseCase.run(rejectedRequest());

        final Order savedOrder = orderRepository.getSavedOrder();
        assertTrue(savedOrder.isRejected());
    }

    @Test(expected = RejectedOrderCannotBeApprovedException.class)
    public void cannotApproveRejectedOrder() throws Exception {
        orderRepository.addOrder(OrderMother.initialRejectedOrder());

        orderApprovalUseCase.run(approvedRequest());

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }

    @Test(expected = ApprovedOrderCannotBeRejectedException.class)
    public void cannotRejectApprovedOrder() throws Exception {
        orderRepository.addOrder(OrderMother.initialApprovedOrder());

        orderApprovalUseCase.run(rejectedRequest());

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }

    @Test(expected = ShippedOrdersCannotBeChangedException.class)
    public void shippedOrdersCannotBeApproved() throws Exception {
        orderRepository.addOrder(OrderMother.initialShippedOrder());

        orderApprovalUseCase.run(approvedRequest());

        assertThat(orderRepository.getSavedOrder(), is(nullValue()));
    }

}
