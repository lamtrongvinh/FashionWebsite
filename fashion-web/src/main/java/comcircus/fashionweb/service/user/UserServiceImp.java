package comcircus.fashionweb.service.user;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.cart.Cart;
import comcircus.fashionweb.model.cart.CartItem;
import comcircus.fashionweb.model.person.user.PasswordResetOtp;
import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.repository.PasswordResetOtpRepository;
import comcircus.fashionweb.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PasswordResetOtpRepository passwordResetOtpRepository;

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
        User user = this.getUser(this.getIdUserByEmail(email));
        boolean isPasswordMatches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user.getEmail().equals(email) && isPasswordMatches) {
            return true;
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
        List<CartItem> list = cart.getCartItem();
        if (!list.isEmpty()) {
            list.clear();
        }
    }

    @Override
    public void changeInfoUser(User user ,String firstName, String lastName, String email) {
        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        user.setEmail(email);
        
        userRepository.save(user);
    }

    @Override
    public boolean changePassword(User user, String newPassword, String currentPassword) {
        boolean check = bCryptPasswordEncoder.matches(currentPassword, user.getPassword());
        if (check) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void createPasswordResetOtpForUser(User user, String otp) {
        PasswordResetOtp passwordResetOtp = new PasswordResetOtp();
        passwordResetOtp.setUser_id(user.getId());
        passwordResetOtp.setOtp(otp);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        passwordResetOtp.setExpiryDate(cal.getTime());
        
        passwordResetOtpRepository.save(passwordResetOtp);
    }

    @Override
    public PasswordResetOtp getPasswordResetOtp(String email) {
        return null;
    }

    @Override
    public void resetPassword(User user, String new_password) {
        user.setPassword(bCryptPasswordEncoder.encode(new_password));
        userRepository.save(user);
    }


}
