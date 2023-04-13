package comcircus.fashionweb.model.person.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.cart.CartPaid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_register")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 20)
    private String first_name;

    @Column(nullable = false, unique = false, length = 20)
    private String last_name;

    @Column(nullable = false, unique = false, length = 50)
    private String email;

    @Column(name = "password", nullable =  false)
    private String password;

    @Column(name = "repeat_password")
    private String repeat_password;

    @Column(name = "role")
    private String role;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    @OneToOne(mappedBy = "user")
    private CartPaid cartPaid;
}
