package comcircus.fashionweb.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.user.User;
import comcircus.fashionweb.repository.UserRepository;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User saveUser(User user) {
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
    
}
