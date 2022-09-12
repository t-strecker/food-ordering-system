package xyz.kida.domain.ports.output.message.publisher.restaurant;

import xyz.kida.domain.event.OrderPaidEvent;
import xyz.kida.domain.event.publisher.DomainEventPublisher;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {

}
