package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.oders.OrderHistory;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    
}
