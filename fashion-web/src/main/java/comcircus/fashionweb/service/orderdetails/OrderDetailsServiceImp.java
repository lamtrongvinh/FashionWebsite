package comcircus.fashionweb.service.orderdetails;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.OrderDetailsDto;
import comcircus.fashionweb.model.cart.CartItemPaid;
import comcircus.fashionweb.model.cart.CartPaid;
import comcircus.fashionweb.model.oders.OrderDetails;
import comcircus.fashionweb.model.person.customer.Customer;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.repository.OrderDetailsRepository;
import comcircus.fashionweb.service.cart.CartPaidService;
import comcircus.fashionweb.service.customer.CustomerService;
import comcircus.fashionweb.service.product.ItemService;
import comcircus.fashionweb.service.product.ProductService;

@Service
public class OrderDetailsServiceImp implements OrderDetailsService{
    @Autowired
    private OrderDetailsRepository oDetailsRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartPaidService cartPaidService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductService productService;

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
            Long user_id = user_login.getId();
            Long order_detail_id = oDetails.getUser_id().getId();
            if (order_detail_id - user_id == 0 && oDetails.getStatus().equals("WAITING")) {
                orderDetailsOfUser.add(oDetails);
            }
        }
        return orderDetailsOfUser;
    }

    @Override
    public List<OrderDetailsDto> changeToOrderDetailsDto(List<OrderDetails> orderDetails) {
        System.out.println(orderDetails.size());
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

    @Override
    public List<OrderDetails> getAllOrderWaiting() {
        List<OrderDetails> orderDetailsWaiting = (List<OrderDetails>) oDetailsRepository.findAll();
        List<OrderDetails> res = new ArrayList<>();
        for (int i = 0; i < orderDetailsWaiting.size(); i++) {
            if(orderDetailsWaiting.get(i).getStatus().equals("WAITING")) {
                res.add(orderDetailsWaiting.get(i));
            }
        }
        return res;
    }
    
    @Override
    public List<OrderDetailsDto> addCustomerInfo(List<OrderDetailsDto> orderDetailsDto) {
        for (int i = 0; i < orderDetailsDto.size(); i++) {
            OrderDetailsDto oDetailsDto = orderDetailsDto.get(i);
            Long customer_id = orderDetailsDto.get(i).getCustomer_id();
            Customer customer = customerService.getCustomer(customer_id);
            oDetailsDto.setFirst_name(customer.getFirst_name());
            oDetailsDto.setLast_name(customer.getLast_name());
            oDetailsDto.setEmail(customer.getEmail());
            oDetailsDto.setPhone_number(customer.getPhone_number());
            oDetailsDto.setAddress(customer.getAddress());

            User user = this.getUserByOrderDetailsId(oDetailsDto.getId());
            List<CartItemPaid> cartItemPaid = this.getCartItemPaidByUser(user);
            oDetailsDto.setCartItemPaid(cartItemPaid);

        }

        return orderDetailsDto;
    }

    @Override
    public List<OrderDetails> getAllOrderDelivery() {
        List<OrderDetails> orderDetailsDelivery = (List<OrderDetails>) oDetailsRepository.findAll();
        List<OrderDetails> res = new ArrayList<>();
        for (int i = 0; i < orderDetailsDelivery.size(); i++) {
            if(orderDetailsDelivery.get(i).getStatus().equals("DELIVERY")) {
                res.add(orderDetailsDelivery.get(i));
            }
        }
        return res;
    }

    @Override
    public void confirmOrder(Long id) {
        try {
            OrderDetails orderDetails = this.oDetailsRepository.findById(id).get();
            orderDetails.setStatus("DELIVERY");
            oDetailsRepository.save(orderDetails);
        } catch (Exception e) {
            System.out.println("confirmOrder ERROR!");
        }
    }

    @Override
    public List<CartItemPaid> getCartItemPaidByUser(User user) {
        List<CartItemPaid> cartItemPaid = (List<CartItemPaid>) user.getCartPaid().getCartItemPaid();
        return cartItemPaid;
    }

    @Override
    public User getUserByOrderDetailsId(Long id) {
        List<OrderDetails> orderDetails = (List<OrderDetails>) this.oDetailsRepository.findAll();
        for (int i = 0; i < orderDetails.size(); i++) {
            if (orderDetails.get(i).getId() == id) {
                User user = new User();
                user = orderDetails.get(i).getUser_id();
                return user;
            }
        }

        return null;
    }

    @Override
    public List<OrderDetails> getAll() {
        return (List<OrderDetails>) this.oDetailsRepository.findAll();
    }

    @Override
    public OrderDetails getById(Long id) {
        List<OrderDetails> list = this.getAll();
        for (int i = 0; i < list.size(); i++) {
            Long order_detail_id = list.get(i).getId();
            if (id - order_detail_id == 0) {
                return list.get(i);
            }
        }

        return null;
    }

    @Override
    public OrderDetailsDto maptoDto(OrderDetails oDetails) {
        OrderDetailsDto oDetailsDto = new OrderDetailsDto();
        oDetailsDto.setId(oDetails.getId());
        oDetailsDto.setCustomer_id(oDetails.getCustomer_id().getCustomer_id());
        oDetailsDto.setUser_id(oDetails.getUser_id().getId());
        oDetailsDto.setStatus(oDetails.getStatus());
        oDetailsDto.setOrder_date(oDetails.getOrder_date());
        oDetailsDto.setTotal_money(oDetails.getTotal_money());
        User user = this.getUserByOrderDetailsId(oDetailsDto.getId());
        List<CartItemPaid> cartItemPaid = this.getCartPaidsByOrderId(user, oDetails.getId());
        oDetailsDto.setCartItemPaid(cartItemPaid);
        return oDetailsDto;
    }

    @Override
    public List<CartItemPaid> getCartPaidsByOrderId(User user, Long id) {
        List<CartItemPaid> list = this.getCartItemPaidByUser(user);
        List<CartItemPaid> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CartItemPaid cartItemPaid = list.get(i);
            Long order_details_id = cartItemPaid.getOrderDetails_id();
            if (order_details_id - id == 0) {
                res.add(cartItemPaid);
            }
        }
        return res;
    }

    @Override
    public void cancelOrder(Long order_id) {
        OrderDetails orderDetails = this.oDetailsRepository.findById(order_id).get();
        User user = orderDetails.getUser_id();
        CartPaid cartPaid = user.getCartPaid();
        List<CartItemPaid> listCartItemPaids = cartPaid.getCartItemPaid();
        for (int i = 0; i < listCartItemPaids.size(); i++) {
            CartItemPaid cartItemPaid = listCartItemPaids.get(i);
            System.out.println(cartItemPaid.getOrderDetails_id());
            if (cartItemPaid.getOrderDetails_id() - order_id == 0) {
                Product product = cartItemPaid.getProduct();
                int quantity = cartItemPaid.getQuantity();
                String size = cartItemPaid.getSize();
                itemService.cancelOrder(product, size, quantity);
                productService.cancelOrder(product, quantity);
                listCartItemPaids.remove(i);
                System.out.println("cancel order_id :" + order_id);
            }
        }
        this.oDetailsRepository.deleteById(order_id);
    }
}
