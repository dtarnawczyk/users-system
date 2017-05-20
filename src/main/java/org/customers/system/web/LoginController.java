package org.customers.system.web;

import org.apache.log4j.Logger;
import org.customers.system.domain.Customer;
import org.customers.system.domain.CustomersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static final Logger log = Logger.getLogger(LoginController.class);

    private CustomersService service;

    public LoginController(CustomersService service){
        this.service = service;
    }

    @GetMapping("/")
    public String homePage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginAttempt(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            Customer customer = service.getCustomer(login, password);

            if(customer != null)
                return "redirect:/logged";
            else {
                redirectAttributes.addFlashAttribute("error", "User not found");
            }
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getLocalizedMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/logged")
    public String logged(){
        return "logged";
    }


}
