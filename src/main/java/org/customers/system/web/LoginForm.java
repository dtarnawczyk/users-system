package org.customers.system.web;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class LoginForm {
    @Size(min=5)
    private String login;
    @Size(min=6)
    private String password;
}
