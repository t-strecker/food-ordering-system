package xyz.kida.domain.event;

import java.time.ZonedDateTime;
import xyz.kida.domain.entity.Order;

public class OrderCancelledEvent extends OrderEvent{

  public OrderCancelledEvent(Order order, ZonedDateTime createdAt) {
    super(order, createdAt);
  }
}
