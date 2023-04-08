package comcircus.fashionweb.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import comcircus.fashionweb.model.person.user.User;
import comcircus.fashionweb.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user.getRole()));
        }
        throw new UsernameNotFoundException("User not found");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roles) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + roles);
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(simpleGrantedAuthority);
        return list;
    }
    
}
