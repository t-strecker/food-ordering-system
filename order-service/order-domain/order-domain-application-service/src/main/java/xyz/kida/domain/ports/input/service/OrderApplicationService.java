package xyz.kida.domain.ports.input.service;

import javax.validation.Valid;
import xyz.kida.domain.dto.create.CreateOrderCommand;
import xyz.kida.domain.dto.create.CreateOrderResponse;
import xyz.kida.domain.dto.track.TrackOrderQuery;
import xyz.kida.domain.dto.track.TrackOrderResponse;

public interface OrderApplicationService {

  CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

  TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);


}
