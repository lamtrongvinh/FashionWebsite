package comcircus.fashionweb.service.orders;


import org.springframework.stereotype.Service;
import comcircus.fashionweb.model.oders.UserOrders;

@Service
public interface UserOrdersService {
    public UserOrders saveUserOrders(UserOrders userOrders);
}
