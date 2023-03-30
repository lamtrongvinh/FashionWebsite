package comcircus.fashionweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.product.ProductService;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {
    @Autowired
    private ProductService productService;

    @GetMapping("/shop")
    public String moveToShop(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        System.out.println(products.get(0).getCategory().getId());
        return "/shopping/shop";
    }

    @GetMapping("/single/{id}")
    public String moveToSingle(@PathVariable Long id, Model model) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);
        return "/shopping/single";
    }

}
