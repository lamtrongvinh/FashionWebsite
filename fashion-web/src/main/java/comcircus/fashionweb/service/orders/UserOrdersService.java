package comcircus.fashionweb.service.orders;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.oders.UserOrders;

@Service
public interface UserOrdersService {
    public UserOrders saveUserOrders(List<OrderDetails> orderDetails);
}
