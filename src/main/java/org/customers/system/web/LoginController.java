package org.customers.system.web;

import org.apache.log4j.Logger;
import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomersService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    private final CustomersService service;

    public LoginController(CustomersService service){
        this.service = service;
    }

    @GetMapping("/")
    public String homePage(LoginForm loginForm) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginForm loginForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        if(formHasErrors(result)) {
            return "login";
        } else {
            return getLoggedViewIfCustomerExists(loginForm, redirectAttributes);
        }
    }

    @GetMapping("/logged")
    public String logged(){
        return "logged";
    }

    private boolean formHasErrors(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    private String getLoggedViewIfCustomerExists(LoginForm form, RedirectAttributes attributes) {
        try {
            Customer customer = service.getCustomer(form.getLogin(), form.getPassword());
            if(customer != null)
                return "redirect:logged";
            else {
                attributes.addFlashAttribute("error", "User not found");
            }
        } catch(Exception e) {
            attributes.addFlashAttribute("error", e.getLocalizedMessage());
        }
        return "redirect:";
    }

}
