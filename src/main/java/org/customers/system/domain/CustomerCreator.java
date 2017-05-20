package org.customers.system.domain;

public interface CustomerCreator {

    Customer createCustomer(Customer customerFactory);

    boolean deleteCustomer(Customer customer);
}
