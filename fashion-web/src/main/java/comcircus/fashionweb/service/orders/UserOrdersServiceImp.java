package comcircus.fashionweb.service.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.oders.UserOrders;
import comcircus.fashionweb.repository.UserOrdersRepository;

@Service
public class UserOrdersServiceImp implements UserOrdersService{
    @Autowired
    private UserOrdersRepository userOrdersRepository;

    @Override
    public UserOrders saveUserOrders(UserOrders userOrders) {

        return userOrdersRepository.save(userOrders);
    }
}
