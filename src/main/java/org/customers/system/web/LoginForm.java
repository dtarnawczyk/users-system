package org.customers.system.web;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class LoginForm {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
