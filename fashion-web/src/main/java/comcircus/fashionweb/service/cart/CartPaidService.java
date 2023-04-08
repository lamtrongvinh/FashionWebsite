package comcircus.fashionweb.service.cart;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.cart.CartItemPaid;
import comcircus.fashionweb.model.cart.CartPaid;

@Service
public interface CartPaidService {
    public CartItemPaid changeCartItemToCartItemPaid(CartItem cartItems, String email, Long orderDetails_id);
    public void changeListCartItemToCartItemPaid(List<CartItem> cartItems, String email, Long orderDetails_id);
    public List<CartPaid> getCartPaids();
    public CartPaid getCartPaidByUserID(Long user_id);
    public CartPaid getCartPaidByOrderDetailsID(Long orderDetail_id);
    public List<CartItemPaid> getCartItemPaidsByOrderDetailID(CartPaid cartPaid, Long orderDetail_id);
}
