package comcircus.fashionweb.model.oders;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordertails")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "order_date")
    private String order_date;

    @Column(name = "total_money")
    private double total_money;

    @OneToOne
    private Customer customer_id;

    @OneToOne
    private User user_id;

    @ManyToOne
    private UserOrders userOrders;

}
