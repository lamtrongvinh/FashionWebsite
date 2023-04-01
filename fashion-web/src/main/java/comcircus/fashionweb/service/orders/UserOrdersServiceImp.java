package comcircus.fashionweb.service.orders;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.oders.UserOrders;
import comcircus.fashionweb.repository.UserOrdersRepository;

@Service
public class UserOrdersServiceImp implements UserOrdersService{
    @Autowired
    private UserOrdersRepository userOrdersRepository;

    @Override
    public UserOrders saveUserOrders(List<OrderDetails> orderDetails) {
        UserOrders userOrders = new UserOrders();
        userOrders.setOrderdetails(orderDetails);

        return userOrdersRepository.save(userOrders);
    }
}
