package comcircus.fashionweb.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import comcircus.fashionweb.config.PaypalPaymentIntent;
import comcircus.fashionweb.config.PaypalPaymentMethod;

import comcircus.fashionweb.dto.CartDto;
import comcircus.fashionweb.dto.CustomerDto;
import comcircus.fashionweb.dto.ItemDetailsCart;
import comcircus.fashionweb.dto.ItemRequestDto;
import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.dto.UserDto;
import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.category.Category;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.PaypalPaymentService;
import comcircus.fashionweb.service.cart.CartPaidService;
import comcircus.fashionweb.service.cart.CartService;
import comcircus.fashionweb.service.category.CategoryService;
import comcircus.fashionweb.service.customer.CustomerService;
import comcircus.fashionweb.service.orderdetails.OrderDetailsService;
import comcircus.fashionweb.service.product.ProductService;
import comcircus.fashionweb.service.product.SizeService;
import comcircus.fashionweb.service.user.UserService;
import comcircus.fashionweb.utils.Utils;


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
    private CartPaidService cartPaidService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private PaypalPaymentService paypalService;

    @PostMapping("/login")
    public String processLogin(HttpServletRequest request, Model model, HttpSession session, @ModelAttribute("userDto") UserDto userDto) {
        String error = "";
        if (userDto == null) {
            model.addAttribute("error", "Please enter your account!");
            return "/login";
        }

        if (userService.checkUserExist(userDto.getEmail(), userDto.getPassword())) {
            session.setAttribute("userDto", userDto);
            
            return "redirect:/auth/homepage";
        }
        error = "Email or password incorrect";
        model.addAttribute("error", error);
        return "/login";    
    }

    @GetMapping("/homepage")
    public String showHomePage(Model model, HttpServletRequest request, HttpSession session) {
        //test session
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        
        String keyword = request.getParameter("keyword");
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
        if (userService.getUser(user_login.getId()) != null) {
            model.addAttribute("user_login", user_login);
            List<Product> bestSellerProducts = productService.getBestSellerProduct();
            model.addAttribute("bestSellerProducts", bestSellerProducts);
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
    public String moveToShopAuth(Model model, HttpServletRequest request, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));

        String keyword = request.getParameter("keyword");
        String category_id = request.getParameter("category_id");
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
    public String moveToContactAuth(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
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
    public String getCart(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
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
    public String showDetailsProduct(@PathVariable Long id, Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
        Product product = productService.getProduct(id);
        if (id > 0 && product != null) {
            model.addAttribute("product", product);
            model.addAttribute("user_login", user_login);
            if (product.getCategory().getName().equals("Clothes") || product.getCategory().getName().equals("Jeans")) {
                model.addAttribute("sizes", sizeService.getListSizeChar());
            }else if (product.getCategory().getName().equals("Sneaker")) {
                model.addAttribute("sizes", sizeService.getListSizeNumber());
            }
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
    public ResponseEntity<HttpStatus> addProductToCart(@PathVariable Long id, Model model, HttpSession session, HttpServletRequest request) {
        String size = request.getParameter("size");
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
        model.addAttribute("user_login", user_login);

        Product product = productService.getProduct(id);
        ItemRequestDto item = new ItemRequestDto(product.getProduct_id(), 1, size);

        CartDto cartDto = cartService.addItemToCart(item, user_login.getEmail());
        List<CartItem> cartItem = cartDto.getCartItem();

        if (!cartItem.isEmpty()) {
            List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
            double total = cartService.getTotalPrice(cartItem);
            model.addAttribute("itemsDetailCart", itemsDetailCart);
            model.addAttribute("total", total);
            model.addAttribute("size", itemsDetailCart.size());
        }

        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    //Delete product from cart
    @GetMapping("/checkout/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProductInCart(Model model, @PathVariable Long id, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
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
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    // Checkout payment
    @GetMapping("/checkout/payment")
    public String processPayment(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
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
    public String handlePayment(@ModelAttribute("customer") CustomerDto customerDto, Model model, HttpSession session, 
                            @RequestParam(value = "flexRadioDefault", required = false) String optionSelected,
                            HttpServletRequest request) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
        model.addAttribute("user_login", user_login);
        //Get cartItem
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        // List<ItemDetailsCart> itemsDetailCart = cartService.changeToItemsDeltails(cartItem);
        double total = cartService.getTotalPrice(cartItem);
        model.addAttribute("total", total);
        model.addAttribute("size", "no-item");

        //Cusomter info
        Customer customer = new Customer();
        CustomerDto cDto = customerService.checkCustomerDtoValid(customerDto);
        if (cDto == null) {
            return "redirect:/auth/checkout/payment";
        }
        customer = customerService.mapCustomerDtoToCustomer(cDto);

        //OrderDetails
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(ldt);

        OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
        orderDetailsDto.setCustomer_id(customer.getCustomer_id());
        orderDetailsDto.setStatus("WAITING");
        orderDetailsDto.setTotal_money(total);
        orderDetailsDto.setOrder_date(formattedDateStr);
        orderDetailsDto.setUser_id(user_login.getId());

        //select payment option
        String cancelUrl = Utils.getBaseURL(request) + "/auth/payment/cancel";
        String successUrl = Utils.getBaseURL(request) + "/auth/payment/success";
        if (optionSelected.equals("option2")) {
            try {
                Payment payment = paypalService.createPayment(
                        total,
                        "USD",
                        PaypalPaymentMethod.paypal,
                        PaypalPaymentIntent.sale,
                        "payment description",
                        cancelUrl,
                        successUrl);
                for(Links links : payment.getLinks()){
                    if(links.getRel().equals("approval_url")){
                        System.out.println("redirect:" + links.getHref());
                        return "redirect:" + links.getHref();
                    }
                }
                } catch (PayPalRESTException e) {
                    System.out.println(e.getMessage());
                }
                return "redirect:/auth/checkout/payment";
        } else {
            //handle save orderDetails
            OrderDetails orderDetails = orderDetailsService.saveOrderDetails(orderDetailsDto, user_login, customer);
            //Add list to history purchase
            // orderHistoryService.addListCartItem(user_login, orderDetails);
            cartPaidService.changeListCartItemToCartItemPaid(cartItem, user_login.getEmail(), orderDetails.getId());

            //delete all cart item after proceed payment
            cartService.deleteAllProduct(user_login);
            return "/auth/checkout/payment-success";
        }

    }

    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, HttpSession session, Model model) {
        try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			if(payment.getState().equals("approved")) {
                //get by session
                OrderDetailsDto orderDetailsDto = (OrderDetailsDto) session.getAttribute("orderDetailsDto");
                User user_login = (User) session.getAttribute("user_login");
                Customer customer = (Customer) session.getAttribute("customer");
                List<CartItem> cartItem = (List<CartItem>) session.getAttribute("cartItem");
                model.addAttribute("user_login", user_login);
                model.addAttribute("size", "no-item");
            //handle save orderDetails
            OrderDetails orderDetails = orderDetailsService.saveOrderDetails(orderDetailsDto, user_login, customer);
            //Add list to history purchase
            cartPaidService.changeListCartItemToCartItemPaid(cartItem, user_login.getEmail(), orderDetails.getId());
            //delete all cart item after proceed payment
            cartService.deleteAllProduct(user_login);
            return "/auth/checkout/payment-success";
        }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "/auth/checkout/payment";
    }
            
    @GetMapping("/payment/cancel")
    public String paymentCancel() {
        return "redirect:/auth/checkout/payment";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
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
    public String getOrdersWaiting(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
        model.addAttribute("user_login", user_login);
        //Get cartItem
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        
        if (!cartItem.isEmpty()) {
            model.addAttribute("size", cartItem.size());
            double total = cartService.getTotalPrice(cartItem);
            model.addAttribute("total", total);
        } else {
            model.addAttribute("size", "no-item");
        }

        List<OrderDetails> orderDetails = orderDetailsService.getWaitingOrderDetailsOfUser(user_login);
        List<OrderDetailsDto> orderDetailsDtotmp = orderDetailsService.changeToOrderDetailsDto(orderDetails);
        List<OrderDetailsDto> orderDetailsDto = orderDetailsService.addCustomerInfoAndCartItemPaid(orderDetailsDtotmp, user_login);
        model.addAttribute("orderDetailsDto", orderDetailsDto);
        

        return "/auth/orders_waiting";
    }
    @GetMapping("/orders/delivery")
    public String getOrdersDelivery(Model model, HttpSession session) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        if (userDto == null) {
            return "/login";
        }
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
        model.addAttribute("user_login", user_login);

        //Get cartItem
        List<CartItem> cartItem = cartService.getCartItems(user_login.getEmail());
        
        if (!cartItem.isEmpty()) {
            model.addAttribute("size", cartItem.size());
            double total = cartService.getTotalPrice(cartItem);
            model.addAttribute("total", total);
        } else {
            model.addAttribute("size", "no-item");
        }

        List<OrderDetails> orderDetails = orderDetailsService.getDeliveryOrderDetailsOfUser(user_login);
        List<OrderDetailsDto> orderDetailsDtotmp = orderDetailsService.changeToOrderDetailsDto(orderDetails);
        List<OrderDetailsDto> orderDetailsDto = orderDetailsService.addCustomerInfoAndCartItemPaid(orderDetailsDtotmp, user_login);

        model.addAttribute("orderDetailsDto", orderDetailsDto);
        return "/auth/orders_delivery";
    }

    @GetMapping("/orders/cancel/{id}")
    public String cancelOrder(HttpSession session, Model model,@PathVariable Long id) {
        orderDetailsService.cancelOrder(id);
         return "/auth/orders/waiting";
    }

    @GetMapping("/profile/update")
    public ResponseEntity<Boolean> changePassword(HttpServletRequest request, HttpSession session, Model model) {
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");
        String email = request.getParameter("email");
        boolean flag = userService.checkEmailExist(email);
        if (email.equals(user_login.getEmail())) {
            flag = false;
        }
        if (!flag && !last_name.equals("")) {
            userService.changeInfoUser(user_login, first_name, last_name, email);
            userDto.setEmail(email);
            session.setAttribute("userDto", userDto);
        }

        if (flag && !last_name.equals("")) {
            userService.changeInfoUser(user_login, first_name, last_name, user_login.getEmail());
        }

        return ResponseEntity.ok(flag);
    }

    @GetMapping("profile/change-password")
    public ResponseEntity<Boolean> changePassword(HttpServletRequest request, HttpSession session) {
        String newPassword = request.getParameter("newPassword");
        String currentPassword = request.getParameter("currentPassword");
        System.out.println(newPassword);
        System.out.println(currentPassword);
        UserDto userDto = (UserDto) session.getAttribute("userDto");
        User user_login = userService.getUser(userService.getIdUserByEmail(userDto.getEmail()));
        boolean checkPassword = userService.changePassword(user_login, newPassword, currentPassword);
        if (checkPassword) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }


}
