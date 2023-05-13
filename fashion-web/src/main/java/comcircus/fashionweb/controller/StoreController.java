package comcircus.fashionweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
    public String getHomeScreen(Model model, HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        List<Product> bestSelleProducts = productService.getBestSellerProduct();
        model.addAttribute("bestSelleProducts", bestSelleProducts);
        if (keyword != null) {
            List<Product> products = productService.getProductsByKeyword(keyword);
            model.addAttribute("products", products);
        } else {
            List<Product> products = productService.getProductsNewArrivals();
            model.addAttribute("products", products);
        }
        return "index";
    }

 
    @GetMapping("/contact")
    public String moveToContact() {

        return "contact";
    }

    @GetMapping("/category")
    public String getCategory() {
        return "/categories/categories";
    }

    
}
