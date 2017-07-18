package org.customers.system.web.config;


import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.profileForm.ProfileFormDto;
import org.customers.system.web.utils.CustomerFormBuilder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomersAuthenticationProvider implements AuthenticationProvider {

    private final CustomersService customersService;

    public CustomersAuthenticationProvider(CustomersService customersService) {
        super();
        this.customersService = customersService;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String login = auth.getPrincipal().toString();
        String password = auth.getCredentials().toString();
        Customer customer = customersService.getCustomer(login, password).
                orElseThrow(() -> new BadCredentialsException("Invalid user or password"));
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_" + customer.getRole().toString()));
        ProfileFormDto profileForm = getProfileFormWithRawPassword(customer, password);
        return new UsernamePasswordAuthenticationToken(profileForm, password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private ProfileFormDto getProfileFormWithRawPassword(Customer customer, String password) {
        customer.setPassword(password);
        return CustomerFormBuilder.buildForm(customer);
    }

}
