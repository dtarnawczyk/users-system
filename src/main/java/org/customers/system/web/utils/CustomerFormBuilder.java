package org.customers.system.web.utils;

import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.profileForm.ProfileFormDto;

import java.time.LocalDate;

public class CustomerFormBuilder {

    public static Customer buildCustomer(ProfileFormDto profileForm) {
        Customer customer = new Customer();
        customer.setLogin(profileForm.getLogin());
        customer.setPassword(profileForm.getPassword());
        customer.setFirstName(profileForm.getFirstName());
        customer.setLastName(profileForm.getLastName());
        customer.setEmail(profileForm.getEmail());
        customer.setAddress(profileForm.getAddress());
        customer.setActive(true);
        customer.setCgroup("USER");
        customer.setCreated(LocalDate.now());
        customer.setModified(LocalDate.now());
        customer.setProfileImage(profileForm.getProfileImage());
        return customer;
    }

    public static ProfileFormDto buildForm(Customer customer) {
        ProfileFormDto profileForm = new ProfileFormDto();
        profileForm.setFirstName(customer.getFirstName());
        profileForm.setLogin(customer.getLogin());
        profileForm.setPassword(customer.getPassword());
        profileForm.setLastName(customer.getLastName());
        profileForm.setAddress(customer.getAddress());
        profileForm.setEmail(customer.getEmail());
        profileForm.setProfileImage(customer.getProfileImage());
        return profileForm;
    }
}
