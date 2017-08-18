package org.customers.system.web.controllers.ui.utils;

import org.customers.system.domain.model.Address;
import org.customers.system.domain.model.AddressBuilder;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.Role;
import org.customers.system.web.controllers.ui.profileForm.ProfileFormDto;

public class CustomerFormBuilder {

    public static Customer convertToEntity(ProfileFormDto profileForm) {
        Customer customer = new Customer();
        customer.setLogin(profileForm.getLogin());
        customer.setPassword(profileForm.getPassword());
        customer.setFirstName(profileForm.getFirstName());
        customer.setLastName(profileForm.getLastName());
        customer.setEmail(profileForm.getEmail());
        customer.setAddress(createAddress(profileForm));
        customer.setActive(true);
        customer.setCgroup("USER");
        customer.setProfileImage(profileForm.getProfileImage());
        customer.setRole(Role.USER);
        return customer;
    }

    public static ProfileFormDto convertToDto(Customer customer) {
        ProfileFormDto profileForm = new ProfileFormDto();
        profileForm.setFirstName(customer.getFirstName());
        profileForm.setLogin(customer.getLogin());
        profileForm.setPassword(customer.getPassword());
        profileForm.setLastName(customer.getLastName());
        profileForm.setCity(customer.getAddress().getCity());
        profileForm.setZipcode(customer.getAddress().getZipcode());
        profileForm.setStreet(customer.getAddress().getStreet());
        profileForm.setEmail(customer.getEmail());
        profileForm.setProfileImage(customer.getProfileImage());
        return profileForm;
    }

    private static Address createAddress(ProfileFormDto profileForm) {
        AddressBuilder addressBuilder = new AddressBuilder();
        addressBuilder.setCity(profileForm.getCity());
        addressBuilder.setStreet(profileForm.getStreet());
        addressBuilder.setZipcode(profileForm.getZipcode());
        return addressBuilder.build();
    }
}
