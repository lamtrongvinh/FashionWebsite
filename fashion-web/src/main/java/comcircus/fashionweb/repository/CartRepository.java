package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.cart.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
