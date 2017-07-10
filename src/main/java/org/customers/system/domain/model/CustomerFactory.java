package org.customers.system.domain.model;

import java.time.LocalDate;

public class CustomerFactory {

    private String login;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;
    private boolean active;
    private String profileImage;
    private String cgroup;
    private LocalDate created;
    private LocalDate modified;
    private Role role;
    private Customer customer = null;

    public CustomerFactory setLogin(String login){
        this.login = login;
        return this;
    }

    public CustomerFactory setFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }

    public CustomerFactory setLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public CustomerFactory setAddress(String address){
        this.address = address;
        return this;
    }

    public CustomerFactory setEmail(String email){
        this.email = email;
        return this;
    }

    public CustomerFactory setPassword(String password){
        this.password = password;
        return this;
    }

    public CustomerFactory setActive(boolean active){
        this.active = active;
        return this;
    }

    public CustomerFactory setProfileImage(String profileImage){
        this.profileImage = profileImage;
        return this;
    }

    public CustomerFactory setCgroup(String customersGroup){
        this.cgroup = customersGroup;
        return this;
    }

    public CustomerFactory setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public CustomerFactory setModified(LocalDate modified) {
        this.modified = modified;
        return this;
    }

    public CustomerFactory setRole(Role role) {
        this.role = role;
        return this;
    }

    public Customer createCustomer() {
        if(login == null || email == null || password == null)
            throw new IllegalStateException("Login or email or password not specified");
        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setActive(active);
        customer.setCgroup(cgroup);
        customer.setCreated(created);
        customer.setModified(modified);
        customer.setProfileImage(profileImage);
        customer.setRole(role);
        this.customer = customer;
        return customer;
    }

    public Customer getCreatedCustomer() {
        return this.customer;
    }
}
