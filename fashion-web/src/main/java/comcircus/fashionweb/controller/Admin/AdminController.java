package comcircus.fashionweb.controller.Admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.dto.ProductDto;
import comcircus.fashionweb.model.cart.CartItemPaid;
import comcircus.fashionweb.model.category.Category;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.category.CategoryService;
import comcircus.fashionweb.service.orderdetails.OrderDetailsService;
import comcircus.fashionweb.service.product.ItemService;
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

    @Autowired
    private ItemService itemService;

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryService.getCategorys());
        return "/admin/add_product";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "/admin/product_list";
    }

    @PostMapping("/addProduct")
    public String addProductpPost(@ModelAttribute("productDto") ProductDto productDto, @RequestParam("file") MultipartFile file) {
        try {
            Category category = categoryService.getCategory(productDto.getCategory_id());
            Product product = productService.mapToProduct(productDto, category);
            // Save file to project directory
            byte[] bytes = file.getBytes();
            Path path = Paths.get("src/main/resources/static/image/" + file.getOriginalFilename());
            Files.write(path, bytes);
            product.setProduct_image_name(file.getOriginalFilename());

            if (productService.checkProductExistByCode(productDto.getProduct_code())) {
                productService.updateProductExitsByCode(product);
            }else {
                productService.saveProduct(product, productDto.getCategory_id());
            }
            
            itemService.saveItem(product, productDto.getSize());
            return "/admin/add_product_succes";
        } catch (IOException e) {
            System.out.println("add Product false");
            return "File upload failed!";
        }

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
    public String updateProductAdmin(@PathVariable Long id, Model model, HttpSession session) {
        if (id > 0 && productService.getProduct(id) != null) {
            Product product = productService.getProduct(id);
            ProductDto productDto = new ProductDto();
            productDto.setProduct_code(product.getProduct_code());
            productDto.setProduct_name(product.getProduct_name());
            productDto.setProduct_desciption(product.getProduct_desciption());
            productDto.setProduct_price(product.getProduct_price());
            productDto.setProduct_discount(product.getProduct_discount());
            productDto.setProduct_id(product.getProduct_id());
            productDto.setProduct_quantity(product.getProduct_quantity());
            productDto.setProduct_live(product.isProduct_live());
            productDto.setProduct_stock(product.isProduct_stock());
            productDto.setCategory_id(product.getCategory().getId());
            productDto.setProduct_image_name(product.getProduct_image_name());

            session.setAttribute("productDto", productDto);
            model.addAttribute("productDto", productDto);
            model.addAttribute("categories", categoryService.getCategorys());
        }

        return "/admin/update_product";
    }

    @PostMapping("/updateProduct")
    public String updateProduct( @ModelAttribute("productDto") ProductDto productDto, @RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            // Save file to project directory
            byte[] bytes = file.getBytes();
            Path path = Paths.get("src/main/resources/static/image/" + file.getOriginalFilename());
            System.out.println("name image :" + file.getOriginalFilename());
            Files.write(path, bytes);
            if (!file.isEmpty()) {
                productService.updateProductFromDto(productDto.getProduct_id(), productDto, file.getOriginalFilename());
            }
            
            return "redirect:/admin/products";
        } catch (IOException e) {
            ProductDto productDto2 = (ProductDto) session.getAttribute("productDto");
            String path_image = productDto2.getProduct_image_name();
            productService.updateProductFromDto(productDto.getProduct_id(), productDto, path_image);
            return "redirect:/admin/products";
        }
        
    }

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "/admin/admin_dashboard";
    }

    @PostMapping("/dashboard")
    public String handleLoginADMIN(HttpServletRequest request) {

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
        for (int i = 0; i < cartItemPaids.size(); i++) {
            Long product_id = cartItemPaids.get(i).getProduct().getProduct_id();
            if (productService.checkProductExist(product_id)) {
                System.out.println("Sản phẩm "+ product_id + "tồn tại!");
            } else {
                System.out.println("Sản phẩm" + product_id+ "hông tồn tại!");
            }
        }
        model.addAttribute("cartItemPaids", cartItemPaids);
        return "/admin/show_order_waiting";
    }

    @GetMapping("/order_confirm/view/{id}")
    public String showOrderConfirmed(Model model, @PathVariable Long id) {
        OrderDetails orderDetails = orderDetailsService.getById(id);
        OrderDetailsDto orderDetailsDto = orderDetailsService.maptoDto(orderDetails);
        List<CartItemPaid> cartItemPaids = orderDetailsDto.getCartItemPaid();
        for (int i = 0; i < cartItemPaids.size(); i++) {
            Long product_id = cartItemPaids.get(i).getProduct().getProduct_id();
            if (productService.checkProductExist(product_id)) {
                System.out.println("Sản phẩm "+ product_id + "tồn tại!");
            } else {
                System.out.println("Sản phẩm" + product_id+ "hông tồn tại!");
            }
        }
        model.addAttribute("cartItemPaids", cartItemPaids);
        return "/admin/show_order_confirm";
    }

    @GetMapping("/items")
    public String showItems(Model model) {
        model.addAttribute("items", itemService.getAllItem());
        return "/admin/items";
    }

}
