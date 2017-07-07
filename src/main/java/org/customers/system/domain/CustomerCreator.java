package org.customers.system.domain;

import org.customers.system.domain.model.Customer;

public interface CustomerCreator {

    Customer createCustomer(Customer customerFactory);

    boolean deleteCustomer(Customer customer);
}
