package org.customers.system.domain;

import org.customers.system.domain.model.Customer;

public interface CustomerActivator {

    void activate(Customer customer);

    void deactivate(Customer customer);

    boolean isActivated(Customer customer);

    boolean isDeactivate(Customer customer);
}
