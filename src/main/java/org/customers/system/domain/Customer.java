package org.customers.system.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer implements Serializable{

    @Id
    @GeneratedValue
    private long id;

    @Size(min = 6)
    @NotEmpty
    @Column()
    private String login;

    private String firstName;
    private String lastName;
    private String address;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private boolean active;
    private String customerGroup;
    private LocalDate created;
    private LocalDate modified;

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getModified() {
        return modified;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setModified(LocalDate modified) {
        this.modified = modified;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if(!(o instanceof Customer))
            return false;
        Customer customer = (Customer) o;
        if(this == customer)
            return true;
        return Objects.equals(id, customer.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }
}

