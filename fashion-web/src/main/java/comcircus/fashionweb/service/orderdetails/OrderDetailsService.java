package comcircus.fashionweb.service.orderdetails;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;

@Service
public interface OrderDetailsService {
    public OrderDetails saveOrderDetails(OrderDetailsDto orderDetailsDto, User user, Customer customer);
    public List<OrderDetails> getWaitingOrderDetailsOfUser(User user_login);
    public List<OrderDetails> getDeliveryOrderDetailsOfUser(User user_login);
    public List<OrderDetailsDto> changeToOrderDetailsDto(List<OrderDetails> orderDetails);
    public List<OrderDetailsDto> addCustomerInfoAndCartItemPaid(List<OrderDetailsDto> orderDetailsDtos, User user_login);
}
