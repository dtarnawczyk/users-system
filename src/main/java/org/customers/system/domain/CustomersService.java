package org.customers.system.domain;

import org.customers.system.domain.model.Customer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomersService {
    Optional<Customer> getCustomer(String login, String password);
    List<Customer> getCustomersByCreationDate(LocalDate date);
    List<Customer> getCustomersByModificationDate(LocalDate date);
}
