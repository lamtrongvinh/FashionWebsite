package comcircus.fashionweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import comcircus.fashionweb.dto.CartDto;
import comcircus.fashionweb.dto.ItemDetailsCart;
import comcircus.fashionweb.dto.ItemRequestDto;
import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.cart.CartService;
import comcircus.fashionweb.service.product.ProductService;
import comcircus.fashionweb.service.user.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;
    
    List<User> user_login_list = new ArrayList<>();

    String emailOfUserLogin = "";

    @PostMapping("/login")
    public String processLogin(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (userService.checkUserExist(email, password))
        {
            User user_login = userService.getUser(userService.getIdUserByEmail(email));
            model.addAttribute("user_login", user_login);
            user_login_list.clear();
            user_login_list.add(user_login);
            List<Product> products = productService.getProducts();
            model.addAttribute("products", products);
            List<CartItem> cartItem = cartService.getCartItems(user_login.getCart(), user_login.getEmail());
            if (!cartItem.isEmpty()) {
                System.out.println("not empty");
                List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
                model.addAttribute("size", itemsDetailCart.size());
            }
            return "/auth/login_success";
        }
        return "redirect:/login";
    }

    @GetMapping("/homepage")
    public String showHomePage(Model model) {
        User user_login = user_login_list.get(0);
        System.out.println(user_login_list.size());
        if (userService.getUser(user_login.getId()) != null) {
            model.addAttribute("user_login", user_login);
            List<Product> products = productService.getProducts();
            model.addAttribute("products", products);
        }
        List<CartItem> cartItem = cartService.getCartItems(user_login.getCart(), user_login.getEmail());
        if (!cartItem.isEmpty()) {
            System.out.println("not empty");
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            model.addAttribute("size", itemsDetailCart.size());
        }
        return "/auth/home_login";
    }


    @GetMapping("/shop")
    public String moveToShopAuth(Model model) {
        User user_login = user_login_list.get(0);
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        model.addAttribute("user_login", user_login);
        List<CartItem> cartItem = cartService.getCartItems(user_login.getCart(), user_login.getEmail());
        if (!cartItem.isEmpty()) {
            System.out.println("not empty");
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            model.addAttribute("size", itemsDetailCart.size());
        }
        return "/auth/auth_shop";
    }

    @GetMapping("/contact")
    public String moveToContactAuth(Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        List<CartItem> cartItem = cartService.getCartItems(user_login.getCart(), user_login.getEmail());
        if (!cartItem.isEmpty()) {
            System.out.println("not empty");
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            model.addAttribute("size", itemsDetailCart.size());
        }
        return "/auth/auth_contact";
    } 
    
    @GetMapping("/checkout")
    public String getCart(Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        
        List<CartItem> cartItem = cartService.getCartItems(user_login.getCart(), user_login.getEmail());

        if (!cartItem.isEmpty()) {
            System.out.println("not empty");
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            double total = cartService.getTotalPrice(cartItem);
            model.addAttribute("itemsDetailCart", itemsDetailCart);
            model.addAttribute("total", total);
            model.addAttribute("size", itemsDetailCart.size());
        }
        return "/auth/cart";
    }

    //Details product
    @GetMapping("/details/{id}")
    public String showDetailsProduct(@PathVariable Long id, Model model) {
        User user_login = user_login_list.get(0);
        Product product = productService.getProduct(id);
        if (id > 0 && product != null) {
            model.addAttribute("product", product);
            model.addAttribute("user_login", user_login);
        }
        List<CartItem> cartItem = cartService.getCartItems(user_login.getCart(), user_login.getEmail());
        if (!cartItem.isEmpty()) {
            System.out.println("not empty");
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            model.addAttribute("size", itemsDetailCart.size());
        }
        return "/auth/details/product_details";
    }

    //add product to cart
    @GetMapping("/checkout/add/{id}")
    public String addProductToCart(@PathVariable Long id, Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);

        Product product = productService.getProduct(id);
        ItemRequestDto item = new ItemRequestDto(product.getProduct_id(), 1);

        CartDto cartDto = cartService.addItemToCart(item, user_login.getEmail());
        List<CartItem> cartItem = cartDto.getCartItem();

        if (!cartItem.isEmpty()) {
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            double total = cartService.getTotalPrice(cartItem);
            model.addAttribute("itemsDetailCart", itemsDetailCart);
            model.addAttribute("total", total);
            model.addAttribute("size", itemsDetailCart.size());
        }

        return "/auth/cart";
    }
    
}
