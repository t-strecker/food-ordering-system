package xyz.kida.domain;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import xyz.kida.domain.dto.create.CreateOrderCommand;
import xyz.kida.domain.dto.create.CreateOrderResponse;
import xyz.kida.domain.event.OrderCreatedEvent;
import xyz.kida.domain.mapper.OrderDataMapper;
import xyz.kida.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;

@Slf4j
@ApplicationScoped
public class OrderCreateCommandHandler {

  private final OrderCreateHelper orderCreateHelper;

  private final OrderDataMapper orderDataMapper;

  private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

  public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper, OrderDataMapper orderDataMapper,
        OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
    this.orderCreateHelper = orderCreateHelper;
    this.orderDataMapper = orderDataMapper;
    this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
  }

  public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
    OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
    orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder());
  }
}
