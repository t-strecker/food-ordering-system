package xyz.kida.domain.event.publisher;

import xyz.kida.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {

  void publish(T domainEvent);
}
