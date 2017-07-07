package org.customers.system.domain;

import org.customers.system.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomersRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByLoginAndPassword(String login, String password);
}
