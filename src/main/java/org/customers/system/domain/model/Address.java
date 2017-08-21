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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (!zipcode.equals(address.zipcode)) return false;
        if (!street.equals(address.street)) return false;
        return city.equals(address.city);
    }

    @Override
    public int hashCode() {
        int result = zipcode.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + city.hashCode();
        return result;
    }
}
