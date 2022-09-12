package xyz.kida.domain.ports.output.message.publisher.payment;

import xyz.kida.domain.event.OrderCreatedEvent;
import xyz.kida.domain.event.publisher.DomainEventPublisher;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {

}
