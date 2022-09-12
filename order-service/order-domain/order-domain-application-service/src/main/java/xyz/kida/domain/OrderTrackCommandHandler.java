package xyz.kida.domain;

import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import xyz.kida.domain.dto.track.TrackOrderQuery;
import xyz.kida.domain.dto.track.TrackOrderResponse;

@Slf4j

@ApplicationScoped
public class OrderTrackCommandHandler {

  public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {

  }
}
