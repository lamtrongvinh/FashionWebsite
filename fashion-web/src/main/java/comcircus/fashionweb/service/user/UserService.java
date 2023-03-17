package comcircus.fashionweb.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.user.User;

@Service
public interface UserService {
    public User getUser(Long id);
    public User saveUser(User user);
    public void deleteUser(Long id);
    public List<User> getUsers();
    public User updateUser(Long id, User user);
}
