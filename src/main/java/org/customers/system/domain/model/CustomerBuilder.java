package org.customers.system.domain.model;

public class CustomerBuilder {

    private String login;
    private String firstName;
    private String lastName;
    private Address address;
    private String email;
    private String password;
    private boolean active;
    private String profileImage;
    private String cgroup;
    private Role role;
    private Customer customer = null;

    public CustomerBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public CustomerBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerBuilder setAddress(Address address) {
        this.address = address;
        return this;
    }

    public CustomerBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public CustomerBuilder setActive(boolean active) {
        this.active = active;
        return this;
    }

    public CustomerBuilder setProfileImage(String profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public CustomerBuilder setCgroup(String customersGroup) {
        this.cgroup = customersGroup;
        return this;
    }

    public CustomerBuilder setRole(Role role) {
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
        customer.setProfileImage(profileImage);
        customer.setRole(role);
        this.customer = customer;
        return customer;
    }

    public Customer getCreatedCustomer() {
        return this.customer;
    }
}
