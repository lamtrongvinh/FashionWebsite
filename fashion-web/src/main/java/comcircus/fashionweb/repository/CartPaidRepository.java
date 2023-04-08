package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.cart.CartPaid;

@Repository
public interface CartPaidRepository extends JpaRepository<CartPaid, Long>{
    
}
