package comcircus.fashionweb.service.orderdetails;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.model.cart.CartItemPaid;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;

@Service
public interface OrderDetailsService {
    public List<OrderDetails> getAll();
    public OrderDetails saveOrderDetails(OrderDetailsDto orderDetailsDto, User user, Customer customer);
    public List<OrderDetails> getWaitingOrderDetailsOfUser(User user_login);
    public List<OrderDetails> getDeliveryOrderDetailsOfUser(User user_login);
    public List<OrderDetailsDto> changeToOrderDetailsDto(List<OrderDetails> orderDetails);
    public List<OrderDetailsDto> addCustomerInfoAndCartItemPaid(List<OrderDetailsDto> orderDetailsDtos, User user_login);
    public List<OrderDetails> getAllOrderWaiting();
    public List<OrderDetails> getAllOrderDelivery();
    public void confirmOrder(Long id);
    public List<OrderDetailsDto> addCustomerInfo(List<OrderDetailsDto> orderDetailsDtos);
    public List<CartItemPaid> getCartItemPaidByUser(User user);
    public User getUserByOrderDetailsId(Long id);
    public OrderDetails getById(Long id);
    public OrderDetailsDto maptoDto(OrderDetails orderDetails);
    public List<CartItemPaid> getCartPaidsByOrderId(User user, Long id);
}
