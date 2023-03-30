package comcircus.fashionweb.dto;

import java.util.ArrayList;
import java.util.List;

import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.person.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {
    private Long id;

    private User user;

    private List<CartItem> cartItem = new ArrayList<>();
}
