package xyz.kida.domain;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import xyz.kida.domain.dto.create.CreateOrderCommand;
import xyz.kida.domain.dto.create.CreateOrderResponse;
import xyz.kida.domain.dto.create.OrderAddress;
import xyz.kida.domain.dto.create.OrderItem;
import xyz.kida.domain.entity.Customer;
import xyz.kida.domain.entity.Order;
import xyz.kida.domain.entity.Product;
import xyz.kida.domain.entity.Restaurant;
import xyz.kida.domain.exception.OrderDomainException;
import xyz.kida.domain.mapper.OrderDataMapper;
import xyz.kida.domain.ports.input.service.OrderApplicationService;
import xyz.kida.domain.ports.output.repository.CustomerRepository;
import xyz.kida.domain.ports.output.repository.OrderRepository;
import xyz.kida.domain.ports.output.repository.RestaurantRepository;
import xyz.kida.domain.valueobject.CustomerId;
import xyz.kida.domain.valueobject.Money;
import xyz.kida.domain.valueobject.OrderId;
import xyz.kida.domain.valueobject.OrderStatus;
import xyz.kida.domain.valueobject.ProductId;
import xyz.kida.domain.valueobject.RestaurantId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(Lifecycle.PER_CLASS)
@QuarkusTest
public class OrderApplicationServiceTest {

  @Inject
  OrderApplicationService orderApplicationService;

  @Inject
  OrderDataMapper orderDataMapper;

  @InjectMock
  OrderRepository orderRepository;

  @InjectMock
  CustomerRepository customerRepository;

  @InjectMock
  RestaurantRepository restaurantRepository;

  private CreateOrderCommand createOrderCommand;
  private CreateOrderCommand createOrderCommandWrongInput;
  private CreateOrderCommand createOrderCommandWrongPrice;

  private final UUID CUSTOMER_ID = UUID.randomUUID();
  private final UUID RESTAURANT_ID = UUID.randomUUID();
  private final UUID PRODUCT_ID = UUID.randomUUID();
  private final UUID ORDER_ID = UUID.randomUUID();
  private final BigDecimal PRICE = new BigDecimal("200.00");

  @BeforeEach
  public void init() {
    createOrderCommand = CreateOrderCommand.builder()
          .customerId(CUSTOMER_ID)
          .restaurantId(RESTAURANT_ID)
          .address(OrderAddress.builder()
                .street("9 Allée du Paradis")
                .postalCode("75019")
                .city("Paris")
                .build())
          .price(PRICE)
          .items(List.of(OrderItem.builder()
                      .productId(PRODUCT_ID)
                      .quantity(1)
                      .price(new BigDecimal("50.00"))
                      .subTotal(new BigDecimal("50.00"))
                .build(),
                OrderItem.builder()
                      .productId(PRODUCT_ID)
                      .quantity(3)
                      .price(new BigDecimal("50.00"))
                      .subTotal(new BigDecimal("150.00"))
                      .build()))
          .build();

    createOrderCommandWrongPrice = CreateOrderCommand.builder()
          .customerId(CUSTOMER_ID)
          .restaurantId(RESTAURANT_ID)
          .address(OrderAddress.builder()
                .street("9 Allée du Paradis")
                .postalCode("75019")
                .city("Paris")
                .build())
          .price(new BigDecimal("250.00"))
          .items(List.of(OrderItem.builder()
                      .productId(PRODUCT_ID)
                      .quantity(1)
                      .price(new BigDecimal("50.00"))
                      .subTotal(new BigDecimal("50.00"))
                      .build(),
                OrderItem.builder()
                      .productId(PRODUCT_ID)
                      .quantity(3)
                      .price(new BigDecimal("50.00"))
                      .subTotal(new BigDecimal("150.00"))
                      .build()))
          .build();

    createOrderCommandWrongInput = CreateOrderCommand.builder()
          .customerId(CUSTOMER_ID)
          .restaurantId(RESTAURANT_ID)
          .address(OrderAddress.builder()
                .street("9 Allée du Paradis")
                .postalCode("75019")
                .city("Paris")
                .build())
          .price(new BigDecimal("260.00"))
          .items(List.of(OrderItem.builder()
                      .productId(PRODUCT_ID)
                      .quantity(1)
                      .price(new BigDecimal("60.00"))
                      .subTotal(new BigDecimal("60.00"))
                      .build(),
                OrderItem.builder()
                      .productId(PRODUCT_ID)
                      .quantity(3)
                      .price(new BigDecimal("50.00"))
                      .subTotal(new BigDecimal("150.00"))
                      .build()))
          .build();

    Customer customer = new Customer();
    customer.setId(new CustomerId(CUSTOMER_ID));

    Restaurant restaurantResponse = Restaurant.builder()
          .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
          .products(List.of(new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))))
          .active(true)
          .build();

    Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
    order.setId(new OrderId(ORDER_ID));

    when(customerRepository.findCustomer(any())).thenReturn(Optional.of(customer));
    when(restaurantRepository.findRestaurantInformation(
          orderDataMapper.createOrderCommandToRestaurant(createOrderCommand))).thenReturn(Optional.of(restaurantResponse));
    when(orderRepository.save(any(Order.class))).thenReturn(Optional.of(order));
  }

  @Test
  public void testOrderCreate() {
    CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
    assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
    assertNotNull(createOrderResponse.getOrderTrackingId());
  }

  @Test
  public void testOrderCreateWithWrongTotalPrice() {
    OrderDomainException exception =
          assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));
    assertEquals("Total price: 250.00 is not equal to Order items total: 200.00", exception.getMessage());
  }
}
