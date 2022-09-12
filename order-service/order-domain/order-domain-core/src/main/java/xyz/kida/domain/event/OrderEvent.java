package xyz.kida.domain.event;

import java.time.ZonedDateTime;
import xyz.kida.domain.entity.Order;

public abstract class OrderEvent implements DomainEvent<Order> {

  private final Order order;
  private final ZonedDateTime createdAt;

  public OrderEvent(Order order, ZonedDateTime createdAt) {
    this.order = order;
    this.createdAt = createdAt;
  }

  public Order getOrder() {
    return order;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }
}
