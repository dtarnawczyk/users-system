package org.customers.system.domain.web;

import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomerCreator;

public class WebCustomerCreator implements CustomerCreator {
    @Override
    public boolean createUser(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteUser(Customer customer) {
        return false;
    }
}
