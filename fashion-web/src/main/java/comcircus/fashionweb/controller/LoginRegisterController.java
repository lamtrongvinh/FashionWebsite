package comcircus.fashionweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import comcircus.fashionweb.dto.UserDto;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.product.ProductService;
import comcircus.fashionweb.service.user.UserService;

@Controller
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/process_register")
    public String processRegister(@Valid User user, BindingResult bindingResult, Model model) {
        String errorMessage = "";
        String userExist = "";
        if (user.getPassword().equals(user.getRepeat_password()) == false  && userService.checkEmailExist(user.getEmail()) == true) {
            errorMessage = "Passwords do not match!";
            model.addAttribute("errorMessage", errorMessage);
            userExist = "Email already in used!";
            model.addAttribute("userExist", userExist);
            return "redirect:/register";
        }
        if (userService.checkEmailExist(user.getEmail()) == true) {
            errorMessage = "Email already in used!";
            model.addAttribute("userExist", errorMessage);
            return "redirect:/register";
        }

        if (user.getPassword().equals(user.getRepeat_password()) == false) {
            errorMessage = "Passwords do not match!";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/register";
        }

        if (user.getPassword().length() < 8) {
            errorMessage = "Passwords is short!";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/register";
        }

        userService.saveUser(user);
        
        return "register_success";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "login"; 
    }


    @GetMapping("/logout")
    public String logout(Model model, HttpSession session, HttpServletRequest request) {
        session.setAttribute("userDto", null);

        String keyword = request.getParameter("keyword");
        System.out.println(keyword);
        if (keyword != null) {
            List<Product> products = productService.getProductsByKeyword(keyword);
            model.addAttribute("products", products);
        } else {
            List<Product> products = productService.getProducts();
            model.addAttribute("products", products);
        }
        return "redirect:/home";

    }
    @GetMapping("/admin-login") 
    public String getFormLoginAdmin (Model model){   
        return "admin-login";
    }

    @GetMapping("/forbidden_exception")
    public String getMessErrorForbidden(Model model) {
        model.addAttribute("message", "You can not access");
        return "/forbidden_exception";
    }
}
