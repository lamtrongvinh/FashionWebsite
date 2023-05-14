package comcircus.fashionweb.controller;

import java.util.Calendar;
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
import comcircus.fashionweb.model.person.user.PasswordResetOtp;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.EmailService;
import comcircus.fashionweb.service.PasswordResetOtpService;
import comcircus.fashionweb.service.product.ProductService;
import comcircus.fashionweb.service.user.UserService;
import comcircus.fashionweb.utils.Utils;

@Controller
public class LoginRegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetOtpService passwordResetOtpService;

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

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/forgot-password-process")
    public String handleForgetPassword(HttpServletRequest request, HttpSession session) {
        String email = request.getParameter("idEmail");
        System.out.println(email);
        String otp = Utils.generateOTP();
        boolean flag = userService.checkEmailExist(email);
        if (flag) {
            User user = userService.getUser(userService.getIdUserByEmail(email));
            session.setAttribute("emailAddress", email);
            userService.createPasswordResetOtpForUser(user, otp);
            emailService.sendOtpEmail(email, otp);
            return "verify_otp";
        } else {
            return "forgot-password";
        }
        
    }

    @PostMapping("/verify-otp")
    public String verifyOTP(HttpServletRequest request, HttpSession session, Model model) {
        String otp = request.getParameter("otp");
        String email = (String) session.getAttribute("emailAddress");
        Long user_id = userService.getIdUserByEmail(email);
        PasswordResetOtp passwordResetOtp = passwordResetOtpService.getPasswordResetOtpByUserID(user_id);
        boolean check = passwordResetOtp.getOtp().equals(otp);
        System.out.println(check);
        if (passwordResetOtp != null) {
            System.out.println("not null");
        }


        if (passwordResetOtp == null || !passwordResetOtp.getOtp().equals(otp)) {
            model.addAttribute("errorMessage", "Invalid OTP");
            return "verify_otp";
        }

        Calendar cal = Calendar.getInstance();
        int checkExpired = passwordResetOtp.getExpiryDate().compareTo(cal.getTime());
        System.out.println(checkExpired);
        if (checkExpired <= 0 ) {
            
            model.addAttribute("errorMessage", "OTP expired!");
            return "verify_otp";
        }

        passwordResetOtpService.deletePasswordResetOtp(passwordResetOtp);

        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(HttpSession session, HttpServletRequest request) {
        String email = (String) session.getAttribute("emailAddress");
        User user = userService.getUser(userService.getIdUserByEmail(email));
        String new_password = request.getParameter("new_password");
        String confirmPassword = request.getParameter("confirmPassword");
        if (!new_password.equals(confirmPassword) || new_password.length() < 8) {
            return "/reset-password";
        } 
        userService.resetPassword(user, new_password);
        return "/change-pass-success";
    }

}
