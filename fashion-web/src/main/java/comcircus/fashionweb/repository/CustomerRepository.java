package comcircus.fashionweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.person.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
