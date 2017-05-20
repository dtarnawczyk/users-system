package org.customers.system.data;

import org.apache.log4j.Logger;
import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.LocalDate;

// Ignored while data is loaded through data-h2.sql file
//@Component
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
        user.setLogin("firstUser");
        user.setFirstName("Tester");
        user.setPassword("test4321");
        user.setActive(true);
        user.setCustomerGroup("USER");
        user.setEmail("test@email.com");
        user.setCreated(LocalDate.now());
        user.setModified(LocalDate.now());
        return user;
    }
}
