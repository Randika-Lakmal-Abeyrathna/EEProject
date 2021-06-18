package com.randikalakmal.adminservice.auth;

import com.randikalakmal.adminservice.model.Permission;
import com.randikalakmal.adminservice.model.Role;
import com.randikalakmal.adminservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserPrinciple implements UserDetails {

    @Autowired
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role:roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
            Set<Permission> permissions = role.getPermissions();
            for(Permission permission: permissions){
                authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.isEnabled();
    }
}
