package org.customers.system.domain;

import org.customers.system.domain.model.Customer;

public interface CustomerCreator {

    Customer create(Customer customerFactory);

    boolean delete(Customer customer);
}
