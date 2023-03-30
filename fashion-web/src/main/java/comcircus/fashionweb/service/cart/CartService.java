package comcircus.fashionweb.service.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.CartDto;
import comcircus.fashionweb.dto.ItemDetailsCart;
import comcircus.fashionweb.dto.ItemRequestDto;
import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.cart.CartItem;

@Service
public interface CartService {

    public CartDto addItemToCart(ItemRequestDto item, String email);

    public List<ItemDetailsCart> changeToItemsDeltails(List<CartItem> cartItem);

    public double getTotalPrice(List<CartItem> cartItem);

    public List<CartItem> getCartItems(Cart cart, String email);
}
