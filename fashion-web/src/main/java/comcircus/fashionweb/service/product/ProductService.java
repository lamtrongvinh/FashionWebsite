package comcircus.fashionweb.service.product;

import java.util.List;


import comcircus.fashionweb.model.product.Product;

public interface ProductService {
    public Product getProduct(Long id);
    public Product saveProduct(Product product, Long category_id);
    public void deleteProduct(Long id);
    public List<Product> getProducts();
    public Product updateProduct(Long id, Product product);
    public Product updateProductCategory(Long id, Product product, Long category_id);
    public int getIndexById(Long id);
}