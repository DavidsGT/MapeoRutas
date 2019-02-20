package com.webServices.rutas.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

	private SegUsuario user;
    private List<String> groups;
 
    public CustomUserDetail(SegUsuario user, List<String> groups) {
        this.user = user;
        this.groups = groups;
    }
 
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
 
    @Override
    public String getPassword() {
        return user.getClave();
    }
 
    @Override
    public String getUsername() {
        return user.getNombre();
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
        return user.getEstado();
    }

}
