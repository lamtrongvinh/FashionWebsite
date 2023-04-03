package comcircus.fashionweb.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import comcircus.fashionweb.dto.CartDto;
import comcircus.fashionweb.dto.CustomerDto;
import comcircus.fashionweb.dto.ItemDetailsCart;
import comcircus.fashionweb.dto.ItemRequestDto;
import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.category.Category;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.cart.CartService;
import comcircus.fashionweb.service.category.CategoryService;
import comcircus.fashionweb.service.customer.CustomerService;
import comcircus.fashionweb.service.orderdetails.OrderDetailsService;
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

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private CategoryService categoryService;
    
    List<User> user_login_list = new ArrayList<>();

    @PostMapping("/login")
    public String processLogin(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String keyword = request.getParameter("keyword");
        if (userService.checkUserExist(email, password))
        {
            User user_login = userService.getUser(userService.getIdUserByEmail(email));
            model.addAttribute("user_login", user_login);
            user_login_list.clear();
            user_login_list.add(user_login);
            System.out.println(keyword);
            if (keyword != null) {
                List<Product> products = productService.getProductsByKeyword(keyword);
                model.addAttribute("products", products);
            } else {
                List<Product> products = productService.getProducts();
                model.addAttribute("products", products);
            }


            if (user_login.getCart() == null) {
                Cart cartOfUer = new Cart();
                user_login.setCart(cartOfUer);
                model.addAttribute("size", "no-item");
            } else {
                List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
                if (!cartItem.isEmpty()) {
                    List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
                    model.addAttribute("size", itemsDetailCart.size());
                } else {
                    model.addAttribute("size", "no-item");
                }
            }
            
            return "/auth/login_success";
        }
        return "redirect:/login";
    }

    @GetMapping("/homepage")
    public String showHomePage(Model model, HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        User user_login = user_login_list.get(0);
        System.out.println(user_login_list.size());
        if (userService.getUser(user_login.getId()) != null) {
            model.addAttribute("user_login", user_login);
            if (keyword != null) {
                List<Product> products = productService.getProductsByKeyword(keyword);
                model.addAttribute("products", products);
            } else {
                List<Product> products = productService.getProducts();
                model.addAttribute("products", products);
            }
        }
        if (user_login.getCart() == null) {
            Cart cartOfUer = new Cart();
            user_login.setCart(cartOfUer);
            model.addAttribute("size", "no-item");
        } else {
            List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
            if (!cartItem.isEmpty()) {
                List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
                model.addAttribute("size", itemsDetailCart.size());
            } else {
                model.addAttribute("size", "no-item");
            }
        }
        return "/auth/home_login";
    }


    @GetMapping("/shop")
    public String moveToShopAuth(Model model, HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        String category_id = request.getParameter("category_id");
        User user_login = user_login_list.get(0);
        List<Category> categories = categoryService.getCategorys();
        model.addAttribute("categories", categories);
        if (category_id != null) {
            List<Product> products = productService.getProductsByCategory(category_id);
            model.addAttribute("products", products);
        }else if (keyword != null) {
            List<Product> products = productService.getProductsByKeyword(keyword);
            model.addAttribute("products", products);
        } else {
            List<Product> products = productService.getProducts();
            model.addAttribute("products", products);
        }
        model.addAttribute("user_login", user_login);
        if (user_login.getCart() == null) {
            Cart cartOfUer = new Cart();
            user_login.setCart(cartOfUer);
            model.addAttribute("size", "no-item");
        } else {
            List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
            if (!cartItem.isEmpty()) {
                List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
                model.addAttribute("size", itemsDetailCart.size());
            } else {
                model.addAttribute("size", "no-item");
            }
        }
        return "/auth/auth_shop";
    }

    @GetMapping("/contact")
    public String moveToContactAuth(Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        if (user_login.getCart() == null) {
            Cart cartOfUer = new Cart();
            user_login.setCart(cartOfUer);
            model.addAttribute("size", "no-item");
        } else {
            List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
            if (!cartItem.isEmpty()) {
                List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
                model.addAttribute("size", itemsDetailCart.size());
            } else {
                model.addAttribute("size", "no-item");
            }
        }
        return "/auth/auth_contact";
    } 
    
    @GetMapping("/checkout")
    public String getCart(Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());

        if (!cartItem.isEmpty()) {
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            double total = cartService.getTotalPrice(cartItem);
            model.addAttribute("itemsDetailCart", itemsDetailCart);
            model.addAttribute("total", total);
            model.addAttribute("size", itemsDetailCart.size());
        } else {
            model.addAttribute("size", "no-item");
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
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        if (!cartItem.isEmpty()) {
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            model.addAttribute("size", itemsDetailCart.size());
        } else {
            model.addAttribute("size", "no-item");
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

    //Delete product from cart
    @GetMapping("/checkout/delete/{id}")
    public String deleteProductInCart(Model model, @PathVariable Long id) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        List<CartItem> cartItem = cartService.deleteProduct(id, user_login.getEmail());

        if (!cartItem.isEmpty()) {
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            double total = cartService.getTotalPrice(cartItem);
            model.addAttribute("itemsDetailCart", itemsDetailCart);
            model.addAttribute("total", total);
            model.addAttribute("size", itemsDetailCart.size());
        } else {
            model.addAttribute("size", "no-item");
        }
        return "/auth/cart";
    }

    // Checkout payment
    @GetMapping("/checkout/payment")
    public String processPayment(Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        
        //Get cartItem
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        if (!cartItem.isEmpty()) {
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            double total = cartService.getTotalPrice(cartItem);
            model.addAttribute("itemsDetailCart", itemsDetailCart);
            model.addAttribute("total", total);
            model.addAttribute("size", itemsDetailCart.size());
        } else {
            model.addAttribute("size", "no-item");
            return "/auth/cart";
        }

        //Add customer
        model.addAttribute("customer", new CustomerDto());
        return "/auth/checkout/payment";
    }

    //Payment
    @PostMapping("/checkout/payment-process")
    public String handlePayment(@ModelAttribute("customer") CustomerDto customerDto, Model model) {
        System.out.println(customerDto.getFirst_name());
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        //Get cartItem
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        // List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
        double total = cartService.getTotalPrice(cartItem);
        model.addAttribute("total", total);
        model.addAttribute("size", "no-item");

        //Cusomter info
        Customer customer = customerService.mapCustomerDtoToCustomer(customerDto);
        
        //OrderDetails
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
        orderDetailsDto.setCustomer_id(customer.getCustomer_id());
        orderDetailsDto.setStatus("WAITING");
        orderDetailsDto.setTotal_money(total);
        orderDetailsDto.setOrder_date(formattedDateStr);
        orderDetailsDto.setUser_id(user_login.getId());
        //handle save orderDetails
        orderDetailsService.saveOrderDetails(orderDetailsDto, user_login, customer);

        //delete all cart item after proceed payment
        cartService.deleteAllProduct(user_login.getEmail());

        return "/auth/checkout/payment-success";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        //Get cartItem
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
        double total = cartService.getTotalPrice(cartItem);
        model.addAttribute("total", total);
        if (!cartItem.isEmpty()) {
            model.addAttribute("size", itemsDetailCart.size());
        } else {
            model.addAttribute("size", "no-item");
        }
        
        return "/auth/profile";
    }

    @GetMapping("/orders/waiting")
    public String getOrdersWaiting(Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        //Get cartItem
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
        double total = cartService.getTotalPrice(cartItem);
        model.addAttribute("total", total);
        if (!cartItem.isEmpty()) {
            model.addAttribute("size", itemsDetailCart.size());
        } else {
            model.addAttribute("size", "no-item");
        }

        List<OrderDetails> orderDetails = orderDetailsService.getWaitingOrderDetailsOfUser(user_login);
        List<OrderDetailsDto> orderDetailsDto = orderDetailsService.changeToOrderDetailsDto(orderDetails);
        for (int i = 0; i < orderDetailsDto.size(); i++) {
            OrderDetailsDto oDetailsDto = orderDetailsDto.get(i);
            Long customer_id = orderDetailsDto.get(i).getCustomer_id();
            Customer customer = customerService.getCustomer(customer_id);
            oDetailsDto.setFirst_name(customer.getFirst_name());
            oDetailsDto.setLast_name(customer.getLast_name());
            oDetailsDto.setEmail(customer.getEmail());
            oDetailsDto.setPhone_number(customer.getPhone_number());
            oDetailsDto.setAddress(customer.getAddress());
        }
        model.addAttribute("orderDetailsDto", orderDetailsDto);

        return "/auth/orders_waiting";
    }
    @GetMapping("/orders/delivery")
    public String getOrdersDelivery(Model model) {
        User user_login = user_login_list.get(0);
        model.addAttribute("user_login", user_login);
        //Get cartItem
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
        double total = cartService.getTotalPrice(cartItem);
        model.addAttribute("total", total);
        if (!cartItem.isEmpty()) {
            model.addAttribute("size", itemsDetailCart.size());
        } else {
            model.addAttribute("size", "no-item");
        }

        List<OrderDetails> orderDetails = orderDetailsService.getDeliveryOrderDetailsOfUser(user_login);
        List<OrderDetailsDto> orderDetailsDto = orderDetailsService.changeToOrderDetailsDto(orderDetails);
        for (int i = 0; i < orderDetailsDto.size(); i++) {
            OrderDetailsDto oDetailsDto = orderDetailsDto.get(i);
            Long customer_id = orderDetailsDto.get(i).getCustomer_id();
            Customer customer = customerService.getCustomer(customer_id);
            oDetailsDto.setFirst_name(customer.getFirst_name());
            oDetailsDto.setLast_name(customer.getLast_name());
            oDetailsDto.setEmail(customer.getEmail());
            oDetailsDto.setPhone_number(customer.getPhone_number());
            oDetailsDto.setAddress(customer.getAddress());
        }
        model.addAttribute("orderDetailsDto", orderDetailsDto);
        return "/auth/orders_delivery";
    }

}
