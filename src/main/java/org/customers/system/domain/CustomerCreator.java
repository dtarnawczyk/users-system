package org.customers.system.domain;

public interface CustomerCreator {

    boolean createUser(Customer customer);

    boolean deleteUser(Customer customer);
}
