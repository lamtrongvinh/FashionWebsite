package comcircus.fashionweb.service.cart;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.cart.CartItemPaid;
import comcircus.fashionweb.model.cart.CartPaid;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.repository.CartPaidRepository;
import comcircus.fashionweb.service.product.ItemService;
import comcircus.fashionweb.service.product.ProductService;
import comcircus.fashionweb.service.user.UserService;

@Service
public class CartPaidServiceImp implements CartPaidService {

    @Autowired
    private CartPaidRepository cartPaidRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ItemService itemService;

    @Override
    public CartItemPaid changeCartItemToCartItemPaid(CartItem cartItem, String email, Long orderDetails_id) {
        
        //CartItem paid
        Product product = cartItem.getProduct();
        itemService.decreaseItemQuantity(product, cartItem.getSize(), cartItem.getQuantity());
        // productService.decreaseQuantity(cartItem.getQuantity(), product.getProduct_id());
        CartItemPaid cartItemPaid = new CartItemPaid();
        cartItemPaid.setQuantity(cartItem.getQuantity());
        cartItemPaid.setProduct(cartItem.getProduct());
        cartItemPaid.setSize(cartItem.getSize());
        productService.decreaseQuantity(cartItem.getQuantity(), cartItem.getProduct().getProduct_id());

        cartItemPaid.setTotal_price(cartItem.getTotal_price());
        cartItemPaid.setOrderDetails_id(orderDetails_id);
        
        return cartItemPaid;
    }

    @Override
    public void changeListCartItemToCartItemPaid(List<CartItem> cartItems, String email, Long orderDetails_id) {
        User user = userService.getUser(userService.getIdUserByEmail(email));
        List<CartItemPaid> items = new ArrayList<>();
        CartPaid cartPaidOfUser = user.getCartPaid();
        if (cartPaidOfUser == null) {
            cartPaidOfUser = new CartPaid();
        } else {
            items = cartPaidOfUser.getCartItemPaid();
        }
        cartPaidOfUser.setUser(user);

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            CartItemPaid cartItemPaid = this.changeCartItemToCartItemPaid(cartItem, email, orderDetails_id);
            cartItemPaid.setCartPaid(cartPaidOfUser);
            items.add(cartItemPaid);
        }
        cartPaidOfUser.setCartItemPaid(items);
        //save
        cartPaidRepository.save(cartPaidOfUser); 
    }

    @Override
    public List<CartPaid> getCartPaids() {
        return (List<CartPaid>) cartPaidRepository.findAll();
    }

    @Override
    public CartPaid getCartPaidByUserID(Long user_id) {
        List<CartPaid> cartPaids = this.getCartPaids();
       
        for (CartPaid cartPaid : cartPaids) {
            if (cartPaid.getUser().getId() == user_id) {
                return cartPaid;
            }
        }
        return null;
    }

    @Override
    public CartPaid getCartPaidByOrderDetailsID(Long orderDetail_id) {
        
        return null;
    }

    @Override
    public List<CartItemPaid> getCartItemPaidsByOrderDetailID(CartPaid cartPaid, Long orderDetail_id) {
        List<CartPaid> cartPaids = this.getCartPaids();
        List<CartItemPaid> listCartItemPaids = new ArrayList<>();
        for (int i = 0; i < cartPaids.size(); i++) {
            if (cartPaids.get(i).getId() - cartPaid.getId() == 0) {
                listCartItemPaids = cartPaids.get(i).getCartItemPaid();
            }
        }

        
        List<CartItemPaid> res = new ArrayList<>();
        for (int i = 0; i < listCartItemPaids.size(); i++) {
            CartItemPaid cartItemPaid = listCartItemPaids.get(i);
            if (cartItemPaid.getOrderDetails_id() - orderDetail_id == 0) {
                res.add(cartItemPaid);
            }
        }

        return res;
    }
    
}
