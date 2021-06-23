package com.safestagram.ws.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class JwtUserDetails implements UserDetails {

    private final String username;
    private final String token;
    private final Long id;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetails(String username, Long id, String token, List<GrantedAuthority> grantedAuthorities){
        this.username = username;
        this.id = id;
        this.token = token;
        this.authorities = grantedAuthorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
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
        return false;
    }

    public String getToken(){
        return token;
    }

    public Long getId(){
        return id;
    }
}
