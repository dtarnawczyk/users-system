package org.customers.system.web.controllers.profileForm;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@Data
public class ProfileForm {
    @Size(min=5)
    @NotEmpty
    protected String login;

    @Size(min=6)
    @NotEmpty
    protected String password;

    @Email
    @NotEmpty
    protected String email;

    protected String firstName;

    protected String lastName;

    protected String address;

    protected String profileImage;

}
