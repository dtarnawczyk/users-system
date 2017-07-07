package org.customers.system.domain;

import org.customers.system.domain.model.Customer;

public interface CustomerMessaging {

    void sendMessage(Customer customer, String message);
}
