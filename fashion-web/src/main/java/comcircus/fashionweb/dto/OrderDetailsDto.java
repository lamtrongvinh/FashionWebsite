package comcircus.fashionweb.dto;

import java.util.List;

import comcircus.fashionweb.model.cart.CartItemPaid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetailsDto {
    private Long id;
    private String status;
    private String order_date;
    private double total_money;
    private Long customer_id;
    private Long user_id;
    private List<CartItemPaid> cartItemPaid;
    //info customer
    private String first_name;
    private String last_name;
    private String phone_number;
    private String address;
    private String url_image;
    private String email;
}
