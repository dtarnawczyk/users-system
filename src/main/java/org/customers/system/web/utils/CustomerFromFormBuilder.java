package org.customers.system.web.utils;

import org.customers.system.domain.Customer;
import org.customers.system.web.controlers.create.CreateForm;

import java.time.LocalDate;

public class CustomerFromFormBuilder {

    public static Customer build(CreateForm createForm) {
        Customer customer = new Customer();
        customer.setLogin(createForm.getLogin());
        customer.setPassword(createForm.getPassword());
        customer.setFirstName(createForm.getFirstName());
        customer.setLastName(createForm.getLastName());
        customer.setEmail(createForm.getEmail());
        customer.setAddress(createForm.getAddress());
        customer.setActive(true);
        customer.setCustomerGroup("USER");
        customer.setCreated(LocalDate.now());
        customer.setModified(LocalDate.now());
        return customer;
    }
}
