package org.customers.system.web.controllers.create;

import org.apache.log4j.Logger;
import org.customers.system.domain.CustomerCreator;
import org.customers.system.domain.model.Customer;
import org.customers.system.web.controllers.profileForm.ProfileForm;
import org.customers.system.web.utils.CustomerFormBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CreateCustomerController {

    private static final Logger log = Logger.getLogger(CreateCustomerController.class);

    private final CustomerCreator service;

    CreateCustomerController(CustomerCreator service){
        this.service = service;
    }

    @GetMapping("/createForm")
    String loadCreatePage(ProfileForm profileForm){
        return "createNewCustomer";
    }

    @PostMapping("/createCustomer")
    String login(@Valid ProfileForm profileForm,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {

        if(formHasErrors(result)) {
            return "createNewCustomer";
        } else {
            Customer customer = CustomerFormBuilder.buildCustomer(profileForm);
            if(saveCustomer(customer)) {
                return "created";
            } else {
                setErrorMessage(redirectAttributes);
                return "redirect:createForm";
            }
        }
    }

    private boolean formHasErrors(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    private void setErrorMessage(RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", "Error creating Customer!");
    }

    private boolean saveCustomer(Customer customer) {
        return service.createCustomer(customer) != null;
    }
}
