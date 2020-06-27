package com.oversea.shipping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.oversea.shipping.auth.service.SecurityService;
import com.oversea.shipping.auth.service.UserService;
import com.oversea.shipping.auth.validator.UserValidator;
import com.oversea.shipping.email.EmailService;
import com.oversea.shipping.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
    private EmailService emailService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "dashboard/login/register";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "dashboard/login/register";
        }
 
        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/dashboard";
    }
    
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("userForm", new User());

        return "dashboard/login/forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
//        userValidator.validate(userForm, bindingResult);
        
        String msg = "You password has been reset.\n\n";
        msg += "Regards\n";
        msg += "Oversea Shipping Team\n";
        
        emailService.sendSimpleMessage("johnlg919@msn.com", "Oversea Shipping - Password Reset", msg);

        return "dashboard/login/forgot-password";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "dashboard/login/login";
    }
}
