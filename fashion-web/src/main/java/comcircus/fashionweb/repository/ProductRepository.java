package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
