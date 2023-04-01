package comcircus.fashionweb.model.oders;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @OneToMany(mappedBy = "userOrders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderdetails;
}
