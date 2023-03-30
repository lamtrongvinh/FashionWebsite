package comcircus.fashionweb.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.service.user.UserService;

@Controller
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/process_register")
    public String processRegister(@Valid User user, BindingResult bindingResult, Model model) {
        if (user.getPassword().equals(user.getRepeat_password()) == false) {
            model.addAttribute("errorMessage", "Passwords do not match!");
            return "register";
        }
        userService.saveUser(user);
        return "register_success";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; 
    }

    @GetMapping("/logout")
    public String logout() {
        return "index";
    }
}