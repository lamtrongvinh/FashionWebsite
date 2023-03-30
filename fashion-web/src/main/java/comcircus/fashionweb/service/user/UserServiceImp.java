package comcircus.fashionweb.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

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
            for (User u : (List<User>) userRepository.findAll()) {
                if (u.getEmail().equals(email)) {
                    return u.getId();
                }
            }
        } catch (Exception e) {
            
        }
        return Long.valueOf(-1);
    }

    
}
