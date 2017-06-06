package org.customers.system.web.controlers.login;

import org.apache.log4j.Logger;
import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomersService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    private final CustomersService service;
    private MessageSource messageSource;

    public LoginController(CustomersService service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;
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
            if(consumerExists(loginForm.getLogin(), loginForm.getPassword())){
                return "redirect:logged";
            } else {
                setErrorMessage(redirectAttributes);
                return "redirect:";
            }
        }
    }

    @GetMapping("/logged")
    public String logged(){
        return "logged";
    }

    private boolean formHasErrors(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    private boolean consumerExists(String login, String password) {
        Customer customer = service.getCustomer(login, password);
        return customer != null ? true : false;
    }

    private void setErrorMessage(RedirectAttributes attributes) {
        Locale current = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage("customerNotFound", null, current);
        attributes.addFlashAttribute("error", localizedErrorMessage);
    }
}
