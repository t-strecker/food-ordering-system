package xyz.kida.domain.event;

import java.time.ZonedDateTime;
import xyz.kida.domain.entity.Order;

public class OrderCreatedEvent extends OrderEvent {

  public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
    super(order, createdAt);
  }
}
