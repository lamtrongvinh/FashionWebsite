package comcircus.fashionweb.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.category.Category;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.repository.CategoryRepository;
import comcircus.fashionweb.repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Product saveProduct(Product product, Long category_id) {
        Category category = categoryRepository.findById(category_id).get();
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getProducts() {

        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product productExist = productRepository.findById(id).get();
        productExist.setProduct_name(product.getProduct_name());
        productExist.setProduct_desciption(product.getProduct_desciption());
        productExist.setProduct_discount(product.getProduct_discount());
        productExist.setProduct_price(product.getProduct_price());
        productExist.setProduct_live(product.isProduct_live());
        productExist.setProduct_stock(product.isProduct_stock());
        productExist.setProduct_image_name(product.getProduct_image_name());
        productExist.setProduct_quantity(product.getProduct_quantity());
        
        return  productRepository.save(productExist);
    }

    @Override
    public int getIndexById(Long id) {
        System.out.println("id :" + id);
        List<Product> products = (List<Product>) productRepository.findAll();
        for (int i = 0 ; i < products.size(); i++ ) {
            if (products.get(i).getProduct_id() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Product updateProductCategory(Long id, Product product, Long category_id) {

        Category category = categoryRepository.findById(category_id).get();
        product.setCategory(category);
        Product productExist = productRepository.findById(id).get();
        productExist.setProduct_name(product.getProduct_name());
        productExist.setProduct_desciption(product.getProduct_desciption());
        productExist.setProduct_discount(product.getProduct_discount());
        productExist.setProduct_price(product.getProduct_price());
        productExist.setProduct_live(product.isProduct_live());
        productExist.setProduct_stock(product.isProduct_stock());
        productExist.setProduct_image_name(product.getProduct_image_name());
        productExist.setProduct_quantity(product.getProduct_quantity());
        productExist.setCategory(product.getCategory());
        
        return  productRepository.save(productExist);
    }
    
}