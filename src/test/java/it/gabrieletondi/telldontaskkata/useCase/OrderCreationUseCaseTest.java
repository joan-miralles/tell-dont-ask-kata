package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Category;
import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.doubles.InMemoryProductCatalog;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderCreationUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private Category food = new Category("food", new BigDecimal("10"));
    private final ProductCatalog productCatalog = new InMemoryProductCatalog(
            Arrays.<Product>asList(
                    new Product("salad", new BigDecimal("3.56"), food),
                    new Product("tomato", new BigDecimal("4.65"), food)
            )
    );
    private final OrderCreationUseCase useCase = new OrderCreationUseCase(orderRepository, productCatalog);

    @Test
    public void sellMultipleItems() throws Exception {
        SellItemRequest saladRequest = new SellItemRequest("salad", 2);
        SellItemRequest tomatoRequest = new SellItemRequest("tomato", 3);
        final SellItemsRequest sellItemsRequest = new SellItemsRequest();
        sellItemsRequest.addSellItem(saladRequest);
        sellItemsRequest.addSellItem(tomatoRequest);

        useCase.run(sellItemsRequest);

        final Order insertedOrder = orderRepository.getSavedOrder();
        assertThat(insertedOrder.getStatus(), is(OrderStatus.CREATED));
        assertThat(insertedOrder.getTotal(), is(new BigDecimal("23.20")));
        assertThat(insertedOrder.getTax(), is(new BigDecimal("2.13")));
        assertThat(insertedOrder.getCurrency(), is("EUR"));
        assertThat(insertedOrder.getItems(), hasSize(2));
        assertThat(insertedOrder.getItems().get(0).getProduct().getName(), is("salad"));
        assertThat(insertedOrder.getItems().get(0).getProduct().getPrice(), is(new BigDecimal("3.56")));
        assertThat(insertedOrder.getItems().get(0).getProduct().getCategory().getName(), is("food"));
        assertThat(insertedOrder.getItems().get(0).getQuantity(), is(2));
        assertThat(insertedOrder.getItems().get(0).getTaxedAmount(), is(new BigDecimal("7.84")));
        assertThat(insertedOrder.getItems().get(0).getTax(), is(new BigDecimal("0.72")));
        assertThat(insertedOrder.getItems().get(1).getProduct().getName(), is("tomato"));
        assertThat(insertedOrder.getItems().get(1).getProduct().getPrice(), is(new BigDecimal("4.65")));
        assertThat(insertedOrder.getItems().get(1).getProduct().getCategory().getName(), is("food"));
        assertThat(insertedOrder.getItems().get(1).getQuantity(), is(3));
        assertThat(insertedOrder.getItems().get(1).getTaxedAmount(), is(new BigDecimal("15.36")));
        assertThat(insertedOrder.getItems().get(1).getTax(), is(new BigDecimal("1.41")));
    }

    @Test(expected = UnknownProductException.class)
    public void unknownProduct() {
        SellItemsRequest sellItemsRequest = new SellItemsRequest();
        SellItemRequest unknownProductRequest = new SellItemRequest("unknown product", 1);
        sellItemsRequest.addSellItem(unknownProductRequest);

        useCase.run(sellItemsRequest);
    }
}
