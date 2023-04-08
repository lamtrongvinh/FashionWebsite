package comcircus.fashionweb.dto;

import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {
    private Long id;
    private int quantity;
    private double total_price;
    private Cart cart;
    private Product product;
}
