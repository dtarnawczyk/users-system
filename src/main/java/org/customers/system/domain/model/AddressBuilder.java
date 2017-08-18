package org.customers.system.domain.model;

public class AddressBuilder {

    private String zipcode;

    private String street;

    private String city;

    public AddressBuilder setZipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public AddressBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder setCity(String city) {
        this.city = city;
        return this;
    }

    public Address build() {
        if (this.city == null || this.street == null || this.zipcode == null) {
            throw new IllegalStateException("City or street or zipcode not specified");
        }
        return new Address(this.street, this.zipcode, this.city);
    }
}
