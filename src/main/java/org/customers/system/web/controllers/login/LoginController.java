package org.customers.system.web.controllers.login;

import org.apache.log4j.Logger;
import org.customers.system.domain.CustomersService;
import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.profileForm.ProfileForm;
import org.customers.system.web.controllers.profileForm.ProfileSession;
import org.customers.system.web.utils.CustomerFormBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Optional;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    private static final String LOGIN_VIEW = "login";
    private static final String REDIRECT_LOGGED_VIEW = "redirect:/logged";

    private final CustomersService service;
    private final MessageSource messageSource;
    private final ProfileSession profileSession;

    @Autowired
    public LoginController(CustomersService service, MessageSource messageSource, ProfileSession profileSession) {
        this.service = service;
        this.messageSource = messageSource;
        this.profileSession = profileSession;
    }

    @ModelAttribute
    public ProfileForm getLoginForm(){
        return getProfileSession().restoreProfile();
    }

    @GetMapping("/")
    public String homePage() {
        if(getProfileSession().isProfileAvailable()){
            return REDIRECT_LOGGED_VIEW;
        } else {
            return LOGIN_VIEW;
        }
    }

    @PostMapping("/login")
    String login(@Valid ProfileForm profileForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {
        if(formHasErrors(result)) {
            return LOGIN_VIEW;
        } else {
            Optional<Customer> customerOptional = getCustomer(profileForm.getLogin(), profileForm.getPassword());
            if(customerOptional.isPresent()){
                saveCustomerInSession(customerOptional.get());
                return REDIRECT_LOGGED_VIEW;
            } else {
                setCustomerNotFoundErrorMessage(redirectAttributes, locale);
                return "redirect:";
            }
        }
    }

    private void saveCustomerInSession(Customer customer) {
        ProfileForm fullProfileForm = CustomerFormBuilder.buildForm(customer);
        getProfileSession().saveProfile(fullProfileForm);
    }

    private ProfileSession getProfileSession() {
        return profileSession;
    }

    private boolean formHasErrors(BindingResult bindingResult) {
        return (bindingResult.getFieldErrors("login").size() > 0 && bindingResult.getFieldErrors("password").size() > 0);
    }

    private Optional<Customer> getCustomer(String login, String password) {
        return service.getCustomer(login, password);
    }

    private void setCustomerNotFoundErrorMessage(RedirectAttributes attributes, Locale locale) {
        String localizedErrorMessage = messageSource.getMessage("customerNotFound", null, locale);
        attributes.addFlashAttribute("error", localizedErrorMessage);
    }

}
