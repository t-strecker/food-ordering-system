package xyz.kida.domain;

import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import xyz.kida.domain.dto.track.TrackOrderQuery;
import xyz.kida.domain.dto.track.TrackOrderResponse;
import xyz.kida.domain.entity.Order;
import xyz.kida.domain.exception.OrderNotFoundException;
import xyz.kida.domain.mapper.OrderDataMapper;
import xyz.kida.domain.ports.output.repository.OrderRepository;
import xyz.kida.domain.valueobject.TrackingId;

@Slf4j
@ApplicationScoped
public class OrderTrackCommandHandler {

  private final OrderDataMapper orderDataMapper;
  private final OrderRepository orderRepository;

  public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
    this.orderDataMapper = orderDataMapper;
    this.orderRepository = orderRepository;
  }

  @Transactional
  public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
    Order order = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()))
          .orElseThrow(() -> {
            log.warn("Could not find order for tracking id {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException(
                  String.format("Could not find order for tracking id %s", trackOrderQuery.getOrderTrackingId()));
          });
    return orderDataMapper.orderToTrackOrderResponse(order);
  }
}
