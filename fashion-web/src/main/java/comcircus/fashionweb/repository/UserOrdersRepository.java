package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.oders.UserOrders;

@Repository
public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {
    
}
