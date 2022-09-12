package xyz.kida.domain.ports.output.repository;

import java.util.Optional;
import xyz.kida.domain.entity.Order;
import xyz.kida.domain.valueobject.TrackingId;

public interface OrderRepository {

  Optional<Order> save(Order order);

  Optional<Order> findByTrackingId(TrackingId trackingId);
}
