package xyz.kida.domain.event;

import java.time.ZonedDateTime;
import xyz.kida.domain.entity.Order;

public class OrderPaidEvent extends OrderEvent{

  public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
    super(order, createdAt);
  }
}
