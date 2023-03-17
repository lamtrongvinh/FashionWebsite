package comcircus.fashionweb.controller.Product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.service.product.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {

        return new ResponseEntity<Product>(productService.getProduct(id), HttpStatus.OK);
    }

    @PostMapping("/category/{categoryId}")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product, @PathVariable Long categoryId) {
        return new ResponseEntity<>(productService.saveProduct(product, categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {

        return new ResponseEntity<List<Product>>(productService.getProducts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product) {
        
        return new ResponseEntity<Product>(productService.updateProduct(id, product) ,HttpStatus.OK);
    }

    @PutMapping("/{id}/category/{categoryId}")
    public ResponseEntity<Product> updateProductCategory(@PathVariable Long id,@RequestBody Product product, @PathVariable Long categoryId) {
        
        return new ResponseEntity<Product>(productService.updateProductCategory(id, product, categoryId) ,HttpStatus.OK);
    }
}
