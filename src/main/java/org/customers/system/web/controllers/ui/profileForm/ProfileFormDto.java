package org.customers.system.web.controllers.ui.profileForm;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@Data
public class ProfileFormDto {
    @Size(min=5)
    @NotEmpty
    protected String login;

    @Size(min=6)
    @NotEmpty
    protected String password;

    @Size(min=6)
    @NotEmpty
    protected String passwordRepeated;

    @Email
    @NotEmpty
    protected String email;

    protected String firstName;

    protected String lastName;

    protected String street;
    protected String zipcode;
    protected String city;

    protected String profileImage;

}
