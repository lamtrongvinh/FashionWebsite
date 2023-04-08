package comcircus.fashionweb.service.cart;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.CartDto;
import comcircus.fashionweb.dto.ItemDetailsCart;
import comcircus.fashionweb.dto.ItemRequestDto;
import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.model.product.Product;
import comcircus.fashionweb.repository.CartRepository;
import comcircus.fashionweb.service.product.ProductService;
import comcircus.fashionweb.service.user.UserService;

@Service
@Transactional
public class CartServiceImp implements CartService{
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartDto addItemToCart(ItemRequestDto item, String email) {
        Long product_id = item.getProduct_id();
        int quantity = item.getQuantity();
        CartDto cartDto = new CartDto();

        Product product = productService.getProduct(product_id);
        if (!product.isProduct_stock()) {
            System.out.println("product not found!");
        }

        double total_price = product.getProduct_price() * quantity;
        System.out.println("Total : " + total_price);

        User user = userService.getUser(userService.getIdUserByEmail(email));
        if (user == null) {
            System.out.println("user not found");
        }
        

        //CartItem
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setTotal_price(total_price);

        //Cart of user
        List<CartItem> items = new ArrayList<>();
        Cart cartOfUser = user.getCart();
        if (cartOfUser == null) {
            cartOfUser = new Cart();
        } else {
            items = cartOfUser.getCartItem();
        }
        cartItem.setCart(cartOfUser); 

        if (items.isEmpty()) {
            items.add(cartItem);
        } else {
            boolean flag = false;
            for (int i = 0; i < items.size(); i++) {
                Long id = items.get(i).getProduct().getProduct_id();
                if (id == product_id) {
                    flag = true;
                    items.get(i).setQuantity(items.get(i).getQuantity() + 1);
                    items.get(i).setTotal_price(items.get(i).getQuantity() * items.get(i).getProduct().getProduct_price());
                    break;
                }
            }

            if (flag == false) {
                items.add(cartItem);
            }
        }

        cartOfUser.setUser(user);
        cartOfUser.setCartItem(items);
        
        //SAVE to db
        cartRepository.save(cartOfUser);

        //set cartDto
        cartDto.setUser(user);
        cartDto.setCartItem(cartOfUser.getCartItem());

        return cartDto;
    }

    @Override
    public List<ItemDetailsCart> changeToItemsDeltails(List<CartItem> cartItem) {
        List<ItemDetailsCart> itemsDetailCart = new ArrayList<>();
        for (int i = 0; i < cartItem.size(); i++) {
            CartItem itemOfList = cartItem.get(i);
            ItemDetailsCart iDetailsCart = new ItemDetailsCart();

            iDetailsCart.setCartItem_id(itemOfList.getId());
            iDetailsCart.setQuantity(itemOfList.getQuantity());
            iDetailsCart.setTotal_price(itemOfList.getTotal_price());
            Product productOfItemsCart = itemOfList.getProduct();
            iDetailsCart.setProduct_id(productOfItemsCart.getProduct_id());
            iDetailsCart.setProduct_price(productOfItemsCart.getProduct_price());
            iDetailsCart.setProduct_desciption(productOfItemsCart.getProduct_desciption());
            iDetailsCart.setProduct_discount(productOfItemsCart.getProduct_discount());
            iDetailsCart.setProduct_quantity(productOfItemsCart.getProduct_quantity());
            iDetailsCart.setProduct_stock(productOfItemsCart.isProduct_stock());
            iDetailsCart.setProduct_image_name(productOfItemsCart.getProduct_image_name());
            iDetailsCart.setProduct_name(productOfItemsCart.getProduct_name());
            iDetailsCart.setProduct_live(productOfItemsCart.isProduct_live());
            iDetailsCart.setCategory_id(productOfItemsCart.getCategory().getId());

            // Add iDetailsCart to List
            itemsDetailCart.add(iDetailsCart);
        }

        return itemsDetailCart;
    }

    @Override
    public double getTotalPrice(List<CartItem> cartItem) {
        double total = 0;
        for (CartItem c : cartItem) {
            total += c.getTotal_price();
        }
        return total;
    }

    @Override
    public List<CartItem> getCartItems(String email) {
        User user = userService.getUser(userService.getIdUserByEmail(email));
        
        List<CartItem> list = new ArrayList<>();
        if (user.getCart() == null) {
            Cart cart = new Cart();
            user.setCart(cart);
            cart.setUser(user);
            cartRepository.save(cart);
            System.out.println("user don't have cart");
        } else {
            System.out.println("user had card but dont have cart item");
            Cart userCart = user.getCart();
            if (userCart.getCartItem() == null) {
                userCart.setCartItem(list);
            } else {
                list = userCart.getCartItem();
            }
        }

        System.out.println(list.size());
        System.out.println("cart dont have item");
        return list;
    }

    @Override
    public List<CartItem> deleteProduct(Long id, String email) {
        User user = userService.getUser(userService.getIdUserByEmail(email));
        List<CartItem> listCartItems = user.getCart().getCartItem();
        for (int i = 0; i < listCartItems.size(); i++) {
            Product product = listCartItems.get(i).getProduct();
            if (product.getProduct_id() == id) {
                listCartItems.remove(listCartItems.get(i));
                System.out.println(listCartItems.size());
            }
        }
        return listCartItems;
    }

    @Override
    public void initCartForUser(User user, Cart cart) {
        User user_login = userService.getUser(userService.getIdUserByEmail(user.getEmail()));
        user_login.setCart(cart);
        cart.setUser(user_login);
    }

    @Override
    public void deleteAllProduct(String email) {
        User user = userService.getUser(userService.getIdUserByEmail(email));
        List<CartItem> listCartItems = user.getCart().getCartItem();
        
        //delete all
        listCartItems.clear();
    }
}
