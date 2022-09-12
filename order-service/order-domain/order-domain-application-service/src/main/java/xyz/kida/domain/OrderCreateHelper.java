package xyz.kida.domain;

import java.util.Optional;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import xyz.kida.domain.dto.create.CreateOrderCommand;
import xyz.kida.domain.entity.Customer;
import xyz.kida.domain.entity.Order;
import xyz.kida.domain.entity.Restaurant;
import xyz.kida.domain.event.OrderCreatedEvent;
import xyz.kida.domain.exception.OrderDomainException;
import xyz.kida.domain.mapper.OrderDataMapper;
import xyz.kida.domain.ports.output.repository.CustomerRepository;
import xyz.kida.domain.ports.output.repository.OrderRepository;
import xyz.kida.domain.ports.output.repository.RestaurantRepository;

@Slf4j
@ApplicationScoped
public class OrderCreateHelper {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderDataMapper orderDataMapper;

  public OrderCreateHelper(OrderDomainService orderDomainService, OrderRepository orderRepository,
        CustomerRepository customerRepository, RestaurantRepository restaurantRepository, OrderDataMapper orderDataMapper) {
    this.orderDomainService = orderDomainService;
    this.orderRepository = orderRepository;
    this.customerRepository = customerRepository;
    this.restaurantRepository = restaurantRepository;
    this.orderDataMapper = orderDataMapper;
  }

  @Transactional
  public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
    checkIfCustomerExists(createOrderCommand.getCustomerId());
    Restaurant restaurant =  getRestaurantIfExists(createOrderCommand);
    Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
    OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
    saveOrder(order);
    log.info("Order created with id {}", orderCreatedEvent.getOrder().getId().getValue());
    return orderCreatedEvent;
  }

  private void checkIfCustomerExists(UUID customerId) {
    Optional<Customer> customer = customerRepository.findCustomer(customerId);
    if (customer.isEmpty()) {
      log.warn("Could not find customer {}", customerId);
      throw new OrderDomainException(String.format("Could not find customer %s", customerId));
    }
  }

  private Restaurant getRestaurantIfExists(CreateOrderCommand createOrderCommand) {
    Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
    return restaurantRepository.findRestaurantInformation(restaurant)
          .orElseThrow(() -> {
            log.warn("Could not find restaurant {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException(String.format("Could not find restaurant %s", createOrderCommand.getRestaurantId()));
          });
  }

  private Order saveOrder(Order order) {
    Order savedOrder = orderRepository.save(order)
          .orElseThrow(() -> {
            log.error("Could not save order");
            throw new OrderDomainException("Could not save order");
          });
    log.info("Order saved with id {}", savedOrder.getId().getValue());
    return savedOrder;
  }
}
