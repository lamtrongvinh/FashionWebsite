package comcircus.fashionweb.service.product;

import java.util.List;

import comcircus.fashionweb.dto.ProductDto;
import comcircus.fashionweb.model.category.Category;
import comcircus.fashionweb.model.product.Product;

public interface ProductService {
    public Product getProduct(Long id);
    public Product saveProduct(Product product, Long category_id);
    public void deleteProduct(Long id);
    public List<Product> getProducts();
    public List<Product> getAllProduct();
    public List<Product> getProductsByKeyword(String keyword);
    public List<Product> getProductsByCategory(String keyword);
    public Product getProductByProductCode(String product_code);
    public Product updateProduct(Long id, Product product);
    public Product updateProductCategory(Long id, Product product, Long category_id);
    public int getIndexById(Long id);
    public Product updateProductFromDto(Long id, ProductDto productDto, String image_name);
    public Product mapToProduct(ProductDto productDto, Category category);
    public void decreaseQuantity(int quantity, Long product_id);
    public void increaseQuantity(int quantity, Long product_id);
    public List<Product> getBestSellerProduct();
    public boolean checkProductExist(Long id);
    public boolean checkProductExistByCode(String product_code);
    public void updateProductExitsByCode(Product product);
    public void cancelOrder(Product product, int quantity);
}
