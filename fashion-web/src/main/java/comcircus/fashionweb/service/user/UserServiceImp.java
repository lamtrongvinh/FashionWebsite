package comcircus.fashionweb.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.UserDto;
import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.repository.CartRepository;
import comcircus.fashionweb.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User saveUser(User user) {
        user.setRole("USER");
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);

    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User exitsUser = userRepository.findById(id).get();
        exitsUser.setFirst_name(user.getFirst_name());
        exitsUser.setLast_name(user.getLast_name());
        exitsUser.setEmail(user.getEmail());
        exitsUser.setPassword(user.getPassword());
        return userRepository.save(exitsUser);
    }

    @Override
    public boolean checkUserExist(String email, String password) {
        List<User> users = (List<User>) userRepository.findAll();
        for (User u : users) {
            boolean isPasswordMatches = bCryptPasswordEncoder.matches(password, u.getPassword());
            if (u.getEmail().equals(email) && isPasswordMatches) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Long getIdUserByEmail(String email) {
        try {
            List<User> users = (List<User>) userRepository.findAll();
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (user.getEmail().equals(email)) {
                    return user.getId();
                }
            }
        } catch (Exception e) {
            System.out.println("user not exist!");
        }
        return Long.valueOf(-1);
    }

    @Override
    public boolean checkEmailExist(String email) {
        List<User> users = (List<User>) userRepository.findAll();
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteAllCartItem(User user) {
        Cart cart = user.getCart();
        List<CartItem> list = cartRepository.findById(cart.getId()).get().getCartItem();
        if (!list.isEmpty()) {
            list.clear();
            if (user.getCart().getCartItem().size() > 0) {
                System.out.println("error");
            }
        }
    }

    @Override
    public User maptoUserByUserDto(UserDto userDto) {
        User user = this.getUser(this.getIdUserByEmail(userDto.getEmail()));
        return user;
    }



}
