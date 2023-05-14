package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.product.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    
}
