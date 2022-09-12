package xyz.kida.domain;

import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import xyz.kida.domain.dto.create.CreateOrderCommand;
import xyz.kida.domain.dto.create.CreateOrderResponse;
import xyz.kida.domain.dto.track.TrackOrderQuery;
import xyz.kida.domain.dto.track.TrackOrderResponse;
import xyz.kida.domain.ports.input.service.OrderApplicationService;

@Slf4j
@ApplicationScoped
class OrderApplicationServiceImpl implements OrderApplicationService {


  private final OrderCreateCommandHandler orderCreateCommandHandler;
  private final OrderTrackCommandHandler orderTrackCommandHandler;

  public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler,
        OrderTrackCommandHandler orderTrackCommandHandler) {
    this.orderCreateCommandHandler = orderCreateCommandHandler;
    this.orderTrackCommandHandler = orderTrackCommandHandler;
  }

  @Override
  public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
    return orderCreateCommandHandler.createOrder(createOrderCommand);
  }

  @Override
  public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
    return orderTrackCommandHandler.trackOrder(trackOrderQuery);
  }
}
