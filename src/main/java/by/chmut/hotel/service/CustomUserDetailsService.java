package by.chmut.hotel.service;

import by.chmut.hotel.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    boolean isEnabled = true;
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        by.chmut.hotel.bean.User user = userDao.getUser(username);

        List<GrantedAuthority> authorities = getAuthorities(user);

        User userForAuthentication = buildUserForAuthentication(user, authorities);

        return userForAuthentication;
    }

    private User buildUserForAuthentication(by.chmut.hotel.bean.User user, List<GrantedAuthority> authorities) {

        User result =
                new User(
                        user.getLogin(),
                        user.getPassword(),
                        isEnabled,
                        accountNonExpired,
                        credentialsNonExpired,
                        accountNonLocked,
                        authorities);

        return result;
    }

    private List<GrantedAuthority> getAuthorities(by.chmut.hotel.bean.User user) {

        List<GrantedAuthority> authList = new ArrayList<>();

        authList.add(new SimpleGrantedAuthority(user.getRole()));

        return authList;
    }

}
