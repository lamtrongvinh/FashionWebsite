package comcircus.fashionweb.service.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.CartDto;
import comcircus.fashionweb.dto.ItemDetailsCart;
import comcircus.fashionweb.dto.ItemRequestDto;
import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.person.user.User;

@Service
public interface CartService {
    public void initCartForUser(User user, Cart cart);
    public CartDto addItemToCart(ItemRequestDto item, String email);
    public List<CartItem> deleteProduct(Long id, String email, String size);
    public void deleteAllProduct(User user);
    public List<ItemDetailsCart> changeToItemsDeltails(List<CartItem> cartItem);
    public double getTotalPrice(List<CartItem> cartItem);
    public List<CartItem> getCartItems(String email);
    public void increaseQuantityItem(ItemDetailsCart item, User user);
    public void decrementQuantityItem(ItemDetailsCart item, User user_login);
}
