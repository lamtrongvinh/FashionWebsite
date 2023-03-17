package comcircus.fashionweb.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 20)
    private String first_name;

    @Column(nullable = false, unique = true, length = 20)
    private String last_name;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password", length = 50)
    private String password;

    @Column(length = 50)
    private String repeat_password;
}
