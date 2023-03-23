package comcircus.fashionweb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import comcircus.fashionweb.model.person.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
}
