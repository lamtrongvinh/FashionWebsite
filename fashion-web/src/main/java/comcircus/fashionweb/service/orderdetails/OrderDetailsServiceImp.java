package comcircus.fashionweb.service.orderdetails;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.model.cart.CartPaid;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.repository.OrderDetailsRepository;
import comcircus.fashionweb.service.cart.CartPaidService;
import comcircus.fashionweb.service.customer.CustomerService;

@Service
public class OrderDetailsServiceImp implements OrderDetailsService{
    @Autowired
    private OrderDetailsRepository oDetailsRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartPaidService cartPaidService;


    @Override
    public OrderDetails saveOrderDetails(OrderDetailsDto orderDetailsDto, User user, Customer customer) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setStatus(orderDetailsDto.getStatus());
        orderDetails.setOrder_date(orderDetailsDto.getOrder_date());
        orderDetails.setTotal_money(orderDetailsDto.getTotal_money());
        orderDetails.setUser_id(user);
        orderDetails.setCustomer_id(customer);
        //save to db
        return oDetailsRepository.save(orderDetails);
    }

    @Override
    public List<OrderDetails> getWaitingOrderDetailsOfUser(User user_login) {
        List<OrderDetails> orderDetails = oDetailsRepository.findAll();
        List<OrderDetails> orderDetailsOfUser = new ArrayList<>();
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetails oDetails = orderDetails.get(i);
            if (oDetails.getUser_id().getId() == user_login.getId() && oDetails.getStatus().equals("WAITING")) {
                orderDetailsOfUser.add(oDetails);
            }
        }
        return orderDetailsOfUser;
    }

    @Override
    public List<OrderDetailsDto> changeToOrderDetailsDto(List<OrderDetails> orderDetails) {
        List<OrderDetailsDto> orderDetailsDto = new ArrayList<>();
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetailsDto oDetailsDto = new OrderDetailsDto();
            OrderDetails oDetails = orderDetails.get(i);
            oDetailsDto.setId(oDetails.getId());
            oDetailsDto.setCustomer_id(oDetails.getCustomer_id().getCustomer_id());
            oDetailsDto.setUser_id(oDetails.getUser_id().getId());
            oDetailsDto.setStatus(oDetails.getStatus());
            oDetailsDto.setOrder_date(oDetails.getOrder_date());
            oDetailsDto.setTotal_money(oDetails.getTotal_money());

            orderDetailsDto.add(oDetailsDto);
        }
        return orderDetailsDto;
    }

    @Override
    public List<OrderDetails> getDeliveryOrderDetailsOfUser(User user_login) {
        List<OrderDetails> orderDetails = oDetailsRepository.findAll();
        List<OrderDetails> orderDetailsOfUser = new ArrayList<>();
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetails oDetails = orderDetails.get(i);
            String delivery = oDetails.getStatus().trim();
            
            if (oDetails.getUser_id().getId() == user_login.getId() && delivery.equals("DELIVERY")) {
                orderDetailsOfUser.add(oDetails);
            }
        }
        return orderDetailsOfUser;
    }

    @Override
    public List<OrderDetailsDto> addCustomerInfoAndCartItemPaid(List<OrderDetailsDto> orderDetailsDto, User user_login) {
        for (int i = 0; i < orderDetailsDto.size(); i++) {
            OrderDetailsDto oDetailsDto = orderDetailsDto.get(i);
            Long customer_id = orderDetailsDto.get(i).getCustomer_id();
            Customer customer = customerService.getCustomer(customer_id);
            oDetailsDto.setFirst_name(customer.getFirst_name());
            oDetailsDto.setLast_name(customer.getLast_name());
            oDetailsDto.setEmail(customer.getEmail());
            oDetailsDto.setPhone_number(customer.getPhone_number());
            oDetailsDto.setAddress(customer.getAddress());
            CartPaid cartPaid = user_login.getCartPaid();
            oDetailsDto.setCartItemPaid(cartPaidService.getCartItemPaidsByOrderDetailID(cartPaid, oDetailsDto.getId()));
        }

        return orderDetailsDto;
    }
    
}
