package comcircus.fashionweb.controller.Admin;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.dto.ProductDto;
import comcircus.fashionweb.model.cart.CartItemPaid;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.category.CategoryService;
import comcircus.fashionweb.service.orderdetails.OrderDetailsService;
import comcircus.fashionweb.service.product.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryService.getCategorys());
        return "/admin/add_product";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "/admin/product_list";
    }

    @PostMapping("/addProduct")
    public String addProductpPost(@ModelAttribute("productDto") ProductDto productDto) {
        Product product = productService.maptoProduct(productDto);

        if (productService.getProduct(productDto.getProduct_id()) != null) {
            Product product_exist = productService.getProduct(productDto.getProduct_id());
            product_exist.setProduct_quantity(product_exist.getProduct_quantity() + productDto.getProduct_quantity());
            productService.saveProduct(product_exist, productDto.getCategory_id());
        } else {
            productService.saveProduct(product, productDto.getCategory_id());
        }

        return "/admin/add_product_succes";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProductAdmin(@PathVariable Long id, Model model) {
        String delete_success = "";
        if (id > 0 && productService.getProduct(id) != null) {
            String product_delete_name = productService.getProduct(id).getProduct_name();
            delete_success = "Delete Success : " + product_delete_name;
            productService.deleteProduct(id);
        }
        
        model.addAttribute("delete_success", delete_success);
        return "redirect:/admin/products";
    }

    @GetMapping("/updateProduct/{id}")
    public String updateProductAdmin(@PathVariable Long id, Model model) {
        if (id > 0 && productService.getProduct(id) != null) {
            Product product = productService.getProduct(id);
            ProductDto productDto = new ProductDto();
            productDto.setProduct_name(product.getProduct_name());
            productDto.setProduct_desciption(product.getProduct_desciption());
            productDto.setProduct_price(product.getProduct_price());
            productDto.setProduct_discount(product.getProduct_discount());
            productDto.setProduct_id(product.getProduct_id());
            productDto.setProduct_quantity(product.getProduct_quantity());
            productDto.setProduct_image_name(product.getProduct_image_name());
            productDto.setProduct_live(product.isProduct_live());
            productDto.setProduct_stock(product.isProduct_stock());
            productDto.setCategory_id(product.getCategory().getId());

            model.addAttribute("productDto", productDto);
            model.addAttribute("categories", categoryService.getCategorys());
        }

        return "/admin/update_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct( @ModelAttribute("productDto") ProductDto productDto) {
        System.out.println("id=" + productDto.getProduct_id());
        productService.updateProductFromDto(productDto.getProduct_id(), productDto);

        return "redirect:/admin/products";
    }

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "/admin/admin_dashboard";
    }

    @PostMapping("/dashboard")
    public String handleLoginADMIN(HttpServletRequest request) {
        // String username = request.getParameter("username");
        // String password = request.getParameter("password");
        // if (username.equals("ADMIN") && password.equals("ADMIN")) {
            
        //     return "/admin/admin_dashboard"; 
        // }
        // return "redirect:/admin-login";
        return "/admin/admin_dashboard"; 
    }

    @GetMapping("/order-waiting")
    public String getOrderWaitingADMIN(Model model) {
        List<OrderDetails> orderDetails = orderDetailsService.getAllOrderWaiting();
        if (orderDetails == null) {
            return "/admin/admin-dashboard";
        }
        List<OrderDetailsDto> orderDetailsDtotmp = orderDetailsService.changeToOrderDetailsDto(orderDetails);
        List<OrderDetailsDto> orderDetailsDto = orderDetailsService.addCustomerInfo(orderDetailsDtotmp);
        model.addAttribute("orderDetailsDtos", orderDetailsDto);

        return "/admin/orders_waiting";
    }

    @GetMapping("/order-confirmed")
    public String getOrderDeliveryADMIN(Model model) {
        List<OrderDetails> orderDetails = orderDetailsService.getAllOrderDelivery();
        if (orderDetails == null) {
            return "redirect:/admin/admin-dashboard";
        }
        List<OrderDetailsDto> orderDetailsDtotmp = orderDetailsService.changeToOrderDetailsDto(orderDetails);
        List<OrderDetailsDto> orderDetailsDto = orderDetailsService.addCustomerInfo(orderDetailsDtotmp);
        model.addAttribute("orderDetailsDtos", orderDetailsDto);

        return "/admin/orders_delivery";
    }

    @GetMapping("/confirm-order/{id}")
    public String confirmedOrder(Model model, @PathVariable Long id) {
        try {
            System.out.println(id);
            orderDetailsService.confirmOrder(Long.valueOf(id));
        } catch (Exception e) {
            System.out.println("Confirmed error");
        }
        return "redirect:/admin/order-waiting";
    }

    @PostMapping("/admin-login")
    public String getAdminDashboard() {
        
        return "/admin/admin-dashboard";
    }

    @GetMapping("/order_waiting/view/{id}")
    public String showOrderWaitingItem(Model model, @PathVariable Long id) {
        OrderDetails orderDetails = orderDetailsService.getById(id);
        OrderDetailsDto orderDetailsDto = orderDetailsService.maptoDto(orderDetails);
        List<CartItemPaid> cartItemPaids = orderDetailsDto.getCartItemPaid();
        model.addAttribute("cartItemPaids", cartItemPaids);
        return "/admin/show_order_waiting";
    }

}
