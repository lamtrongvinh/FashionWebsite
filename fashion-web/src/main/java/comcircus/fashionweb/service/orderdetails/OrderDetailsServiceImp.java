package comcircus.fashionweb.service.orderdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.repository.OrderDetailsRepository;

@Service
public class OrderDetailsServiceImp implements OrderDetailsService{
    @Autowired
    private OrderDetailsRepository oDetailsRepository;

    @Override
    public void saveOrderDetails(OrderDetailsDto orderDetailsDto, User user, Customer customer) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setStatus(orderDetailsDto.getStatus());
        orderDetails.setOrder_date(orderDetailsDto.getOrder_date());
        orderDetails.setTotal_money(orderDetailsDto.getTotal_money());
        orderDetails.setUser_id(user);
        orderDetails.setCustomer_id(customer);

        //save to db
        oDetailsRepository.save(orderDetails);
    }
    
}
