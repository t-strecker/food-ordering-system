package xyz.kida.domain.ports.output.repository;

import java.util.Optional;
import xyz.kida.domain.entity.Restaurant;

public interface RestaurantRepository {

  Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);

}
