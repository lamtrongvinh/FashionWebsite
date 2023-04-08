package comcircus.fashionweb.service.orderHistory;


import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.oders.OrderHistory;
import comcircus.fashionweb.model.person.user.User;

@Service
public interface OrderHistoryService {
    public void addListCartItem(User user, OrderDetails orderDetails);
    public List<OrderHistory> getAllOrderHistory();
    public List<Cart> getListCartByUser(User user);
    public Cart getCartByOrderDetailsId(Long orderDetailsId);
}
