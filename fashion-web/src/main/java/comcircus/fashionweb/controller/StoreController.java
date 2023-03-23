package comcircus.fashionweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.product.ProductService;

@Controller
public class StoreController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = {"/", "/home"})
    public String getHomeScreen(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

 
    @GetMapping("/contact")
    public String moveToContact() {

        return "contact";
    }

    @GetMapping("/shop")
    public String moveToShop(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "shop";
    }

}
