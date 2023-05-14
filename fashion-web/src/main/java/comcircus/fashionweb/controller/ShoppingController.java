package comcircus.fashionweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import comcircus.fashionweb.model.category.Category;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.category.CategoryService;
import comcircus.fashionweb.service.product.ProductService;
import comcircus.fashionweb.service.product.SizeService;

@Controller
@RequestMapping("/shopping")
public class ShoppingController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SizeService sizeService;

    @GetMapping("/shop")
    public String moveToShop(Model model, HttpServletRequest request, Pageable pageable) {
        String keyword = request.getParameter("keyword");
        List<Category> categories = categoryService.getCategorys();
        model.addAttribute("categories", categories);
        String category_id_string = request.getParameter("category_id");

        if (category_id_string != null) {
            // List<Product> products = productService.getProductsByCategory(category_id_string);
            Long category_id = Long.valueOf(category_id_string);
            Page<Product> products = productService.findByCategory_Id(category_id, pageable);
            model.addAttribute("products", products);
        }else if (keyword != null) {
            List<Product> products = productService.getProductsByKeyword(keyword);
            model.addAttribute("products", products);
        } else {
            Page<Product> products = productService.findAll(pageable);
            model.addAttribute("products", products);
        }
        return "/shopping/shop";
    }

    @GetMapping("/single/{id}")
    public String moveToSingle(@PathVariable Long id, Model model) {
        Product product = productService.getProduct(id);
        if (product.getCategory().getName().equals("Clothes") || product.getCategory().getName().equals("Jeans")) {
            model.addAttribute("sizes", sizeService.getListSizeChar());
        }else if (product.getCategory().getName().equals("Sneaker")) {
            model.addAttribute("sizes", sizeService.getListSizeNumber());
        }
        model.addAttribute("product", product);
        return "/shopping/single";
    }

}
