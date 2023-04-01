package comcircus.fashionweb.service.orderdetails;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;

@Service
public interface OrderDetailsService {
    public void saveOrderDetails(OrderDetailsDto orderDetailsDto, User user, Customer customer);
}
