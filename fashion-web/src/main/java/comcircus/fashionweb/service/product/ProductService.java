package comcircus.fashionweb.service.product;

import java.util.List;

import comcircus.fashionweb.dto.ProductDto;
import comcircus.fashionweb.model.product.Product;

public interface ProductService {
    public Product getProduct(Long id);
    public Product saveProduct(Product product, Long category_id);
    public void deleteProduct(Long id);
    public List<Product> getProducts();
    public List<Product> getProductsByKeyword(String keyword);
    
    public List<Product> getProductsByCategory(String keyword);
    public Product updateProduct(Long id, Product product);
    public Product updateProductCategory(Long id, Product product, Long category_id);
    public int getIndexById(Long id);
    public Product updateProductFromDto(Long id, ProductDto productDto);

    public void decreaseQuantity(int quantity, Long product_id);
}
