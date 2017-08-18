package org.customers.system.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

    @NotNull
    @Column(nullable = false, length = 6)
    private final String zipcode;

    @NotNull
    @Column(nullable = false)
    private final String street;

    @NotNull
    @Column(nullable = false)
    private final String city;

    @SuppressWarnings("unused")
    private Address() {
        this.zipcode = null;
        this.street = null;
        this.city = null;
    }

    public Address(String street, String zipcode, String city){
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }
}
