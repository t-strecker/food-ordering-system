package xyz.kida.domain.ports.output.message.publisher.payment;

import xyz.kida.domain.event.OrderCancelledEvent;
import xyz.kida.domain.event.publisher.DomainEventPublisher;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {

}
