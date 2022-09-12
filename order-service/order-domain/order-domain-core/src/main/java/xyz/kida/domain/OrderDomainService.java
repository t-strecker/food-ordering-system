package xyz.kida.domain;

import java.util.List;
import xyz.kida.domain.entity.Order;
import xyz.kida.domain.entity.Restaurant;
import xyz.kida.domain.event.OrderCancelledEvent;
import xyz.kida.domain.event.OrderCreatedEvent;
import xyz.kida.domain.event.OrderPaidEvent;

public interface OrderDomainService {

  OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

  OrderPaidEvent payOrder(Order order);

  void approveOrder(Order order);

  OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

  void cancelOrder(Order order, List<String> failureMessages);
}
