package org.customers.system.domain;

import org.customers.system.domain.model.Customer;
import org.customers.system.service.exception.CustomerNotFoundException;

public interface CustomerEditor {

    void updateUsersFirstName(Customer customer, String firstName);

    void updateUsersLastName(Customer customer, String lastName);

    void updateUsersAddress(Customer customer, String address);

    void updateUsersEmail(Customer customer, String email);

    void updatePassword(Customer customer, String password);

    void updateCgroup(Customer customer, String group);

    void updateActive(Customer customer, String group);

    Customer updateCustomer(Customer existingCustomer) throws CustomerNotFoundException;
}
