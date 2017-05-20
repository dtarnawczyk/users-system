package org.customers.system.domain;

import java.time.LocalDate;
import java.util.List;

public interface CustomersService {
    Customer getCustomer(String login, String password);
    List<Customer> getCustomersByCreationDate(LocalDate date);
    List<Customer> getCustomersByModificationDate(LocalDate date);
}
