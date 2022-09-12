package xyz.kida.domain.ports.output.repository;

import java.util.Optional;
import java.util.UUID;
import xyz.kida.domain.entity.Customer;

public interface CustomerRepository {

  Optional<Customer> findCustomer(UUID customerId);

}
