package org.customers.system.repository;

import org.apache.log4j.Logger;
import org.customers.system.domain.CustomersRepository;
import org.customers.system.domain.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class FirstUserLoader implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = Logger.getLogger(FirstUserLoader.class);

    private static final String FIRST_USER_LOGIN = "firstUser";
    private static final String FIRST_USER_PASSWORD = "test4321";

    private CustomersRepository usersRepository;

    @Autowired
    public FirstUserLoader(CustomersRepository repository){
        this.usersRepository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<Customer> optional = usersRepository.findByLoginAndPassword(FIRST_USER_LOGIN, FIRST_USER_PASSWORD);
        if(!optional.isPresent()) {
            Customer firstUser = creatingFirstCustomer();
            usersRepository.save(firstUser);
            log.info("First user created with id: " +firstUser.getId());

        }

        Iterable<Customer> iterableUsers = usersRepository.findAll();
        iterableUsers.forEach(u -> {
            log.info("User found: " + u.getLogin());
        });
    }

    private Customer creatingFirstCustomer() {
        Customer user = new Customer();
        user.setLogin("firstUser");
        user.setFirstName("Tester");
        user.setPassword("test4321");
        user.setActive(true);
        user.setCgroup("USER");
        user.setEmail("test@email.com");
        user.setCreated(LocalDate.now());
        user.setModified(LocalDate.now());
        return user;
    }
}
