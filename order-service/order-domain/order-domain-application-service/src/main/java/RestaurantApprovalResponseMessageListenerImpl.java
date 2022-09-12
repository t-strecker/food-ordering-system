import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import xyz.kida.domain.dto.message.RestaurantApprovalResponse;
import xyz.kida.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;

@Slf4j
@ApplicationScoped
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {

  @Override
  public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {

  }

  @Override
  public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

  }
}
