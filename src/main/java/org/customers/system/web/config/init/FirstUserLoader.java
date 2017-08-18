package org.customers.system.web.config.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.model.Address;
import org.customers.system.domain.model.Customer;
import org.customers.system.domain.model.Role;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("dev")
public class FirstUserLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final String FIRST_USER_LOGIN = "firstUser";
    private static final String FIRST_USER_PASSWORD = "test4321";

    private final CustomersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<Customer> optional = usersRepository.findByLogin(FIRST_USER_LOGIN);
        if(!optional.isPresent()) {
            Customer firstUser = creatingFirstCustomer();
            usersRepository.save(firstUser);
            log.info("First user created with id: " +firstUser.getId());

        }

        Iterable<Customer> users = usersRepository.findAll();
        users.forEach(u -> log.info("User found: " + u.getLogin()));
    }

    private Customer creatingFirstCustomer() {
        Customer user = new Customer();
        user.setLogin("firstUser");
        user.setFirstName("Tester");
        user.setPassword(passwordEncoder.encode("test4321"));
        user.setActive(true);
        user.setCgroup("USER");
        user.setEmail("test@email.com");
        Address address = new Address("Zamkowa", "12-345", "Sosnowiec");
        user.setAddress(address);
        user.setRole(Role.USER);
        return user;
    }
}
