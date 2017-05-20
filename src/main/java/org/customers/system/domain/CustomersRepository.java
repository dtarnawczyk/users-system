package org.customers.system.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends CrudRepository<Customer, Long> {

    Customer findByLoginAndPassword(String login, String password);
}
