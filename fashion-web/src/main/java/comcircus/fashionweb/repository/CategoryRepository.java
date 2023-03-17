package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
