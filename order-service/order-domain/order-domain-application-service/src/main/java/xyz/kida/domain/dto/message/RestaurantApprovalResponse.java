package xyz.kida.domain.dto.message;

import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import xyz.kida.domain.valueobject.OrderApprovalStatus;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalResponse {
  private String id;
  private String sagaId;
  private String orderId;
  private String restaurantId;
  private Instant createdAt;
  private OrderApprovalStatus orderApprovalStatus;
  private List<String> failureMessages;
}