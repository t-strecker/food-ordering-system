package xyz.kida.domain.mapper;

import java.util.List;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import xyz.kida.domain.dto.create.CreateOrderCommand;
import xyz.kida.domain.dto.create.CreateOrderResponse;
import xyz.kida.domain.dto.create.OrderAddress;
import xyz.kida.domain.dto.create.OrderItem;
import xyz.kida.domain.entity.Order;
import xyz.kida.domain.entity.Product;
import xyz.kida.domain.entity.Restaurant;
import xyz.kida.domain.valueobject.CustomerId;
import xyz.kida.domain.valueobject.Money;
import xyz.kida.domain.valueobject.ProductId;
import xyz.kida.domain.valueobject.RestaurantId;
import xyz.kida.domain.valueobject.StreetAddress;

@ApplicationScoped
public class OrderDataMapper {

  public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
    return Restaurant.builder()
          .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
          .products(createProductsFromItems(createOrderCommand.getItems()))
          .build();
  }

  private List<Product> createProductsFromItems(List<OrderItem> orderItems) {
    return orderItems
          .stream()
          .map(orderItem -> new Product(new ProductId(orderItem.getProductId())))
          .toList();
  }

  public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
    return Order.builder()
          .customerId(new CustomerId(createOrderCommand.getCustomerId()))
          .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
          .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
          .price(new Money(createOrderCommand.getPrice()))
          .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
          .build();
  }

  private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
    return new StreetAddress(
          UUID.randomUUID(),
          orderAddress.getStreet(),
          orderAddress.getPostalCode(),
          orderAddress.getCity()
    );
  }

  private List<xyz.kida.domain.entity.OrderItem> orderItemsToOrderItemEntities(List<OrderItem> orderItems) {
    return orderItems.stream()
          .map(orderItem ->
                xyz.kida.domain.entity.OrderItem.builder()
                      .product(new Product(new ProductId(orderItem.getProductId())))
                      .price(new Money(orderItem.getPrice()))
                      .quantity(orderItem.getQuantity())
                      .subTotal(new Money(orderItem.getSubTotal()))
                      .build())
          .toList();
  }

  public CreateOrderResponse orderToCreateOrderResponse(Order order) {
    return CreateOrderResponse.builder()
          .orderTrackingId(order.getTrackingId().getValue())
          .orderStatus(order.getOrderStatus())
          .build();
  }

}
