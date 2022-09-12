package xyz.kida.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.kida.domain.entity.Order;
import xyz.kida.domain.entity.Product;
import xyz.kida.domain.entity.Restaurant;
import xyz.kida.domain.event.OrderCancelledEvent;
import xyz.kida.domain.event.OrderCreatedEvent;
import xyz.kida.domain.event.OrderPaidEvent;
import xyz.kida.domain.exception.OrderDomainException;

public class OrderDomainServiceImpl implements OrderDomainService {

  private static final String UTC = "UTC";
  Logger log = LoggerFactory.getLogger(OrderDomainServiceImpl.class.getSimpleName());

  @Override
  public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
    validateRestaurant(restaurant);
    setOrderProductInformation(order, restaurant);
    order.validate();
    order.initialize();
    log.info("Order {} is initiated", order.getId().getValue());
    return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
  }

  @Override
  public OrderPaidEvent payOrder(Order order) {
    order.pay();
    log.info("Order {} is paid", order.getId().getValue());
    return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
  }

  @Override
  public void approveOrder(Order order) {
    order.approve();
    log.info("Order {} is approved", order.getId().getValue());
  }

  @Override
  public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
    order.initCancel(failureMessages);
    log.info("Order payment for order {} is cancelling", order.getId().getValue());
    return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
  }

  @Override
  public void cancelOrder(Order order, List<String> failureMessages) {
    order.cancel(failureMessages);
    log.info("Order {} is cancelled", order.getId().getValue());
  }

  private void validateRestaurant(Restaurant restaurant) {
    if (!restaurant.isActive()) {
      throw new OrderDomainException(String.format("Restaurant %s is not active.", restaurant.getId().getValue()));
    }
  }

  private void setOrderProductInformation(Order order, Restaurant restaurant) {
    // TODO: use a hashmap for products for avoiding this double for each call
    order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct -> {
      Product currentProduct = orderItem.getProduct();
      if (currentProduct.equals(restaurantProduct)) {
        currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
      }
    }));
  }
}
