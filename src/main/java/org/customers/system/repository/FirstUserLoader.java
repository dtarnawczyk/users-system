package org.customers.system.repository;

import org.apache.log4j.Logger;
import org.customers.system.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class FirstUserLoader implements ApplicationListener<ContextRefreshedEvent> {

    private CustomersRepository usersRepository;

    private Logger log = Logger.getLogger(FirstUserLoader.class);

    @Autowired
    public FirstUserLoader(CustomersRepository repository){
        this.usersRepository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Customer firstUser = creatingFirstCustomer();
        usersRepository.save(firstUser);

        log.info("First user created with id:" +firstUser.getId());

        Iterable<Customer> iterableUsers = usersRepository.findAll();
        iterableUsers.forEach(u -> {
            log.info("User found: " + u.getLogin());
        });
    }

    private Customer creatingFirstCustomer() {
        Customer user = new Customer();
        user.setLogin("test");
        user.setFirstName("Tester");
        user.setPassword("test4321");
        user.setActive(true);
        user.setCustomerGroup("USER");
        user.setEmail("test@email.com");
        user.setCreated(Date.valueOf(LocalDate.now()));
        user.setModified(Date.valueOf(LocalDate.now()));
        return user;
    }
}
