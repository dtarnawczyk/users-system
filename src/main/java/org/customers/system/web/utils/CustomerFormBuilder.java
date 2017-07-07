package org.customers.system.web.utils;

import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.profileForm.ProfileForm;

import java.time.LocalDate;

public class CustomerFormBuilder {

    public static Customer buildCustomer(ProfileForm profileForm) {
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

    public static ProfileForm buildForm(Customer customer) {
        ProfileForm profileForm = new ProfileForm();
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
