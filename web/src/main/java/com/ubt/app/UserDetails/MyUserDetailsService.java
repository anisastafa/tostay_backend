package com.ubt.app.UserDetails;

import com.ubt.app.security.ApplicationUserRole;
import com.ubt.app.security.HostPrincipal;
import com.ubt.app.security.UserPrincipal;
import com.ubt.model.Host;
import com.ubt.model.User;
import com.ubt.repository.HostRepository;
import com.ubt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.ubt.app.security.ApplicationUserRole.HOST;
import static com.ubt.app.security.ApplicationUserRole.USER;
import static org.springframework.security.core.userdetails.User.*;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HostRepository hostRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lastDigits = username.substring(username.length() - 3);
        User user;
        Host host;

        if (username.contains("@")) {
            host = hostRepository.findByEmail(username);
            if (host == null) {
                throw new UsernameNotFoundException("Server 404");
            }
//            return withUsername(host.getEmail()).password(host.getPassword())
//                    .authorities(HOST.getGrantedAuthorities()).build();
//            return new org.springframework.security.core.userdetails.User(
//                    host.getEmail(), host.getPassword(), HOST.getGrantedAuthorities()
//            );
//            return builder()
//                    .username(host.getEmail())
//                    .password(host.getPassword())
//                    .roles(HOST.name()) // ROLE_HOST
//                    .build();
//            return org.springframework.security.core.userdetails.User.withUsername(host.getEmail())
//                    .password(host.getPassword())
//                    .roles("HOST").build();
            return new HostPrincipal(host);
        }
        user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Server 404");
        }
        System.out.println("user.getUsername: "+user.getUsername());
        System.out.println("user.password: "+user.getPassword());
        System.out.println("userDetailsService authorities of user: "+user.getUsername()+" " +
                "these authorities: "+USER.getGrantedAuthorities());
//        return withUsername(user.getUsername()).password(user.getPassword())
//                .authorities(USER.getGrantedAuthorities()).build();
//        return new org.springframework.security.core.userdetails.User
//                (user.getUsername(), user.getPassword(), USER.getGrantedAuthorities());
//        return builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(USER.name()) // ROLE_USER
//                .build();
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
//        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
//                .password(user.getPassword())
//                .roles("USER").build();
        return new UserPrincipal(user);
    }
}
