package comcircus.fashionweb.controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import comcircus.fashionweb.dto.ProductDto;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.category.CategoryService;
import comcircus.fashionweb.service.product.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

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
        Product product = new Product();
        product.setProduct_id(productDto.getProduct_id());
        product.setProduct_name(productDto.getProduct_name());
        product.setProduct_desciption(productDto.getProduct_desciption());
        product.setProduct_price(productDto.getProduct_price());
        product.setProduct_discount(productDto.getProduct_discount());
        product.setProduct_quantity(productDto.getProduct_quantity());
        product.setProduct_live(productDto.isProduct_live());
        product.setProduct_stock(productDto.isProduct_stock());
        product.setProduct_image_name(productDto.getProduct_image_name());
        product.setCategory(categoryService.getCategory(productDto.getCategory_id()));
        System.out.println(product.toString());

        productService.saveProduct(product, productDto.getCategory_id());

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
}
