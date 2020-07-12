package com.oversea.shipping.controller;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.oversea.shipping.auth.service.SecurityService;
import com.oversea.shipping.auth.service.UserService;
import com.oversea.shipping.auth.validator.UserValidator;
import com.oversea.shipping.dao.UserRepository;
import com.oversea.shipping.email.EmailService;
import com.oversea.shipping.model.User;

@Controller
@SessionAttributes("user")
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
    
    @Autowired
	private UserRepository userRepository;
    
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "dashboard/login/register";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
        	List<ObjectError> errors = bindingResult.getAllErrors();
            model.addAttribute("alertMessage", errors.get(0).getDefaultMessage());
            model.addAttribute("alertType", "danger");
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
    public String forgotPassword(Model model, @ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        
        String username = userForm.getUsername();
        
        User user = userRepository.findByUsernameAndActiveTrue(username);
        
        if(user!= null)
        {
        	Calendar c = Calendar.getInstance(); 
        	c.setTime(new Date()); 
        	c.add(Calendar.DATE, 1);
        	Date expiryDate = c.getTime();
        	
        	user.setResetPassword(true);
        	user.setResetExpiry(expiryDate);
        	userRepository.save(user);
        	
            final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            
            String encEmail = Base64.getEncoder().encodeToString(username.getBytes());
            String url = baseUrl + "/user/reset-password/" + URLEncoder.encode(encEmail);
            
            // TODO: email template
            String msg = "Dear User,\n\n";
            msg += "Please click the below link within 24 hours to reset your password.\n";
            msg += url + "\n\n";
            msg += "Regards\n";
            msg += "Oversea Shipping Team\n";
            
            emailService.sendSimpleMessage(username, "Oversea Shipping - Password Reset", msg);
            
            model.addAttribute("alertMessage", "Please check your email to proceed password reset.");
            model.addAttribute("alertType", "success");
        }
        else
        {
            model.addAttribute("alertMessage", "Email not found.");
            model.addAttribute("alertType", "danger");
        }

        return "dashboard/login/forgot-password";
    }
    
    @GetMapping("/reset-password/{code}")
    public String resetPassword(Model model, @PathVariable(value = "code") String code) {
    	
        User user = null;
        try {
            String decEmail = new String(Base64.getDecoder().decode(code));
            user = userRepository.findByUsernameAndActiveTrue(decEmail);
        } catch (Exception e) {
            // cannot decode code
        }
    	
    	if(user == null)
    	{
    		// alert: 403 Forbidden
    		model.addAttribute("alertMessage", "403 Forbidden");
    		return "site/blank-with-message";
    	}
    	else
    	{
    		Date now = new Date();
    		if(user.isResetPassword() && null != user.getResetExpiry() && user.getResetExpiry().compareTo(now) > 0)
    		{
    			user.setPassword(null);
    			user.setPasswordConfirm(null);
    			model.addAttribute("user", user);
    			
    			return "dashboard/login/reset-password";
    		}
    		else
    		{
    			// alert: Password reset expired.
    			model.addAttribute("alertMessage", "Password reset expried.");
    			return "site/blank-with-message";
    		}
    	}
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(Model model, @ModelAttribute("user") User user) {
        
    	String password = user.getPassword();
    	String passwordConfirm = user.getPasswordConfirm();
    	
    	boolean flagSucc = true;
    	if(password.length() < 8)
    	{
    		model.addAttribute("alertMessage", "Password must be at least 8 characters long.");
    		model.addAttribute("alertType", "danger");
    		flagSucc = false;
    	}
    	else if(!password.equals(passwordConfirm))
    	{
    		model.addAttribute("alertMessage", "Password and confirm password do not match.");
    		model.addAttribute("alertType", "danger");
    		flagSucc = false;
    	}
    	
    	if(flagSucc)
    	{
    		user.setPassword(bCryptPasswordEncoder.encode(password));
    		user.setResetPassword(false);
    		user.setResetExpiry(null);
    		userRepository.save(user);
    		
    		model.addAttribute("alertMessage", "Password reset successful!");
            model.addAttribute("alertType", "success");
    	}
    	
    	model.addAttribute("flagSucc", flagSucc);

    	return "dashboard/login/reset-password";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
        	model.addAttribute("alertType", "danger");
        	model.addAttribute("alertMessage", "Your username and password is invalid.");
        }
            
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "dashboard/login/login";
    }
}
