package comcircus.fashionweb.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.ProductDto;
import comcircus.fashionweb.model.category.Category;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.repository.CategoryRepository;
import comcircus.fashionweb.repository.ProductRepository;
import comcircus.fashionweb.service.category.CategoryService;

@Service
public class ProductServiceImp implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

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
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            product = null;
            productRepository.deleteById(id);
        }
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

    @Override
    public Product updateProductFromDto(Long id, ProductDto productDto, String image_name) {

        Product productExist = productRepository.findById(id).get();
        productExist.setProduct_code(productDto.getProduct_code());
        productExist.setProduct_name(productDto.getProduct_name());
        productExist.setProduct_desciption(productDto.getProduct_desciption());
        productExist.setProduct_price(productDto.getProduct_price());
        productExist.setProduct_discount(productDto.getProduct_discount());
        productExist.setProduct_id(productDto.getProduct_id());
        productExist.setProduct_quantity(productDto.getProduct_quantity());
        productExist.setProduct_image_name(image_name);
        productExist.setProduct_live(true);
        productExist.setProduct_stock(true);
        productExist.setCategory(categoryService.getCategory(productDto.getCategory_id()));
        
        return  productRepository.save(productExist);
    }

    @Override
    public List<Product> getProductsByKeyword(String keyword) {
        keyword = keyword.toLowerCase();
        List<Product> list =  (List<Product>) productRepository.findAll();
        List<Product> listByKeyword = new ArrayList<>();
        for (int i = 0;i < list.size(); i++) {
            if (list.get(i).getProduct_name().toLowerCase().contains(keyword) || list.get(i).getProduct_desciption().toLowerCase().contains(keyword)) {
                listByKeyword.add(list.get(i));
            }
        }

        return listByKeyword;
    }

    @Override
    public List<Product> getProductsByCategory(String keyword) {
        List<Product> listByKeyword = new ArrayList<>();
        List<Product> list = (List<Product>) productRepository.findAll();
        try {
            Long category_id = Long.valueOf(keyword);
            for (int i = 0; i < list.size(); i++) {
                Product product = list.get(i);
                if (product.getCategory().getId() == category_id) {
                    listByKeyword.add(product);
                }
            }
        } catch (Exception e) {
            System.out.println("can not convert to Long by keyword");
        }

        return listByKeyword;
    }

    @Override
    public void decreaseQuantity(int quantity, Long product_id) {
        List<Product> list = this.getProducts();
        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            if (product.getProduct_id() == product_id) {
                int product_quantity = product.getProduct_quantity() - quantity >= 0 ? product.getProduct_quantity() - quantity : 0 ;
                product.setProduct_quantity(product_quantity);
                if (product.getProduct_quantity() <= 0) {
                    product.setProduct_live(false);
                    product.setProduct_stock(false);
                }
            }
        }
    }

    @Override
    public void increaseQuantity(int quantity, Long product_id) {
        List<Product> list = this.getProducts();
        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            if (product.getProduct_id() == product_id) {
                int product_quantity = product.getProduct_quantity() + quantity;
                product.setProduct_quantity(product_quantity);
            }
        }
    }

    @Override
    public List<Product> getBestSellerProduct() {
        List<Product> bestSeller = new ArrayList<>();
        for (Product p : this.getProducts()) {
            if (p.getCategory().getId() == 1L) {
                bestSeller.add(p);
            }
        }
        return bestSeller;
    }

    @Override
    public boolean checkProductExist(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public Product mapToProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setProduct_id(productDto.getProduct_id());
        product.setProduct_code(productDto.getProduct_code());
        product.setProduct_name(productDto.getProduct_name());
        product.setProduct_desciption(productDto.getProduct_desciption());
        product.setProduct_price(productDto.getProduct_price());
        product.setProduct_discount(productDto.getProduct_discount());
        product.setProduct_quantity(productDto.getProduct_quantity());
        product.setProduct_live(true);
        product.setProduct_stock(true);
        product.setCategory(categoryService.getCategory(productDto.getCategory_id()));
        return product;
    }

    @Override
    public boolean checkProductExistByCode(String product_code) {
        List<Product> list = this.getProducts();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProduct_code().equals(product_code)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateProductExitsByCode(Product product) {
        Product productExist = this.getProductByProductCode(product.getProduct_code());
        productExist.setProduct_quantity(productExist.getProduct_quantity() + product.getProduct_quantity());

        productRepository.save(productExist);
    }

    @Override
    public Product getProductByProductCode(String product_code) {
        try {
            Product productExist = new Product();
            List<Product> list = this.getProducts();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getProduct_code().equals(product_code)) {
                    productExist = list.get(i);
                }
            }

            return productExist;
        } catch (Exception e) {
            System.out.println("product_code not exist!");
            return null;
        }
    }

}
