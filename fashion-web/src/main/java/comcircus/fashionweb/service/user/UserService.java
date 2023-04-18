package comcircus.fashionweb.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import comcircus.fashionweb.dto.UserDto;
import comcircus.fashionweb.model.person.user.User;

@Service
public interface UserService {
    public User getUser(Long id);
    public User saveUser(User user);
    public void deleteUser(Long id);
    public List<User> getUsers();
    public User updateUser(Long id, User user);
    public boolean checkUserExist(String email, String password);
    public boolean checkEmailExist(String email);
    public Long getIdUserByEmail(String email);
    public void deleteAllCartItem(User user);
    public User maptoUserByUserDto(UserDto userDto);
}
