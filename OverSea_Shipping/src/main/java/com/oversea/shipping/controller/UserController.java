package com.oversea.shipping.controller;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
//            System.out.println("enc:"+encEmail);
//            String decEmail = new String(Base64.getDecoder().decode(encEmail));
//            System.out.println("dec:"+decEmail);
            
            String url = baseUrl + "/user/reset-password/" + URLEncoder.encode(encEmail);
            
            // TODO: email template
            String msg = "Dear User,\n\n";
            msg += "Please click the below link within 24 hours to reset your password.\n";
            msg += url + "\n\n";
            msg += "Regards\n";
            msg += "Oversea Shipping Team\n";
            
            emailService.sendSimpleMessage(username, "Oversea Shipping - Password Reset", msg);
        }
        else
        {
        	System.out.println("alert user not exist");
        }

        return "dashboard/login/forgot-password";
    }
    
    @GetMapping("/reset-password/{code}")
    public String resetPassword(Model model, @PathVariable(value = "code") String code) {
    	
    	String decEmail = new String(Base64.getDecoder().decode(code));

    	System.out.println("reset user:" + decEmail);
    	
    	User user = userRepository.findByUsernameAndActiveTrue(decEmail);
    	
    	if(user == null)
    	{
    		// alert: 403 Forbidden
    		model.addAttribute("alertMsg", "403 Forbidden");
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
    			model.addAttribute("alertMsg", "Password reset expried.");
    		}
    	}

        return "dashboard/login/reset-password";
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(Model model, @ModelAttribute("user") User user) {
        
    	String password = user.getPassword();
    	String passwordConfirm = user.getPasswordConfirm();
    	
    	boolean flagSucc = true;
    	if(password.length() < 8)
    	{
    		model.addAttribute("alertMsg", "Password must be at least 8 characters long.");
    		flagSucc = false;
    	}
    	else if(!password.equals(passwordConfirm))
    	{
    		model.addAttribute("alertMsg", "Password and confirm password do not match.");
    		flagSucc = false;
    	}
    	
    	if(flagSucc)
    	{
    		user.setPassword(bCryptPasswordEncoder.encode(password));
    		user.setResetPassword(false);
    		user.setResetExpiry(null);
    	}

    	return "dashboard/login/reset-password";
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
