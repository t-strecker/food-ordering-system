package xyz.kida.domain;

import javax.enterprise.context.ApplicationScoped;
import xyz.kida.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import xyz.kida.domain.ports.output.repository.CustomerRepository;
import xyz.kida.domain.ports.output.repository.OrderRepository;
import xyz.kida.domain.ports.output.repository.RestaurantRepository;

import static org.mockito.Mockito.mock;

public class OrderTestConfiguration {

  @ApplicationScoped
  RestaurantRepository restaurantRepository() {
    return mock(RestaurantRepository.class);
  }

  @ApplicationScoped
  CustomerRepository customerRepository() {
    return mock(CustomerRepository.class);
  }

  @ApplicationScoped
  OrderRepository orderRepository() {
    return mock(OrderRepository.class);
  }

  @ApplicationScoped
  OrderDomainService orderDomainService() {
    return new OrderDomainServiceImpl();
  }

  @ApplicationScoped
  OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
    return mock(OrderCreatedPaymentRequestMessagePublisher.class);
  }

}
