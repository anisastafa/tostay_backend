package com.ubt.app.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ubt.app.security.ApplicationUserPermission.*;


public enum ApplicationUserRole {
    USER(Sets.newHashSet(USER_READ, USER_WRITE, APARTMENT_READ)),
    HOST(Sets.newHashSet(HOST_READ, HOST_WRITE, APARTMENT_READ, APARTMENT_WRITE));


    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Collection<? extends GrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        System.out.println("type of role logged in:---  "+this.name());
        System.out.println("permissions: "+permissions);
        return permissions;
    }
}
