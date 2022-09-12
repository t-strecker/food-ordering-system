package xyz.kida.domain.ports.input.message.listener.restaurantapproval;

import xyz.kida.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

  void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

  void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);

}
