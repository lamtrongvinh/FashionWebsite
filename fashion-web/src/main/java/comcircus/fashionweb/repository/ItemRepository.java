package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.product.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
    
}
