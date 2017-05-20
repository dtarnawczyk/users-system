package org.customers.system.service;

import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomerActivator;

public class BaseCustomerActivator implements CustomerActivator {

    @Override
    public void activate(Customer customer) {

    }

    @Override
    public void deactivate(Customer customer) {

    }

    @Override
    public boolean isActivated(Customer customer) {
        return false;
    }

    @Override
    public boolean isDeactivate(Customer customer) {
        return false;
    }
}
