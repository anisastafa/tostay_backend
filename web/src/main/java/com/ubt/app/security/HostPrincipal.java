package com.ubt.app.security;
import com.ubt.model.Host;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static com.ubt.app.security.ApplicationUserRole.HOST;

public class HostPrincipal implements UserDetails {

    private final Host host;
    public HostPrincipal(Host host) {
        super();
        this.host = host;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("host.name() --- :"+HOST.name());
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+HOST.name()));
    }

    @Override
    public String getPassword() {
        return host.getPassword();
    }

    @Override
    public String getUsername() {
        return host.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
