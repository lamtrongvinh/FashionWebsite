package comcircus.fashionweb.service.orderHistory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.oders.OrderHistory;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.repository.OrderHistoryRepository;

@Service
public class OrderHistoryServiceImp implements OrderHistoryService{

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Override
    public void addListCartItem(User user, OrderDetails orderDetails) {
        Cart cart = user.getCart();
        System.out.println("size :" + user.getCart().getCartItem().size());
        OrderHistory orderHistory = new OrderHistory();
        if (cart != null) {
            System.out.println("cart is not null");
            System.out.println(cart.getCartItem().size());
        }
        orderHistory.setUser_id(user.getId());
        orderHistory.setOrderDetails_id(orderDetails.getId());
        orderHistoryRepository.save(orderHistory);
    }

    @Override
    public List<OrderHistory> getAllOrderHistory() {
        return (List<OrderHistory>) orderHistoryRepository.findAll();
    }

    @Override
    public List<Cart> getListCartByUser(User user) {
        List<OrderHistory> list = (List<OrderHistory>) orderHistoryRepository.findAll();
        List<Cart> listCart = new ArrayList<>();
        Cart cart = new Cart();
        for (int i = 0; i < list.size(); i++) {
            OrderHistory orderHistory = list.get(i);
            if (orderHistory.getUser_id() == user.getId()) {
                listCart.add(cart);
            }
        }

        return listCart;
    }

    @Override
    public Cart getCartByOrderDetailsId(Long orderDetailsId) {
        Cart cart = new Cart();
        return cart;
    }
    
}
