package com.schlader.james.jwtauthexample.models.dto;

import com.schlader.james.jwtauthexample.models.entity.MyUserPrincipal;
import com.schlader.james.jwtauthexample.models.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyUserDTO {
    private String username;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private Set<GrantedAuthority> authorities;
    private Set<Role> roles;

    public MyUserDTO(MyUserPrincipal myUserPrincipal){
        this.username = myUserPrincipal.getUsername();
        this.isAccountNonExpired = myUserPrincipal.isAccountNonExpired();
        this.isAccountNonLocked = myUserPrincipal.isAccountNonLocked();
        this.isCredentialsNonExpired = myUserPrincipal.isCredentialsNonExpired();
        this.isEnabled = myUserPrincipal.isEnabled();
        this.authorities = new HashSet<>(myUserPrincipal.getAuthorities());
        this.roles = myUserPrincipal.getRoles();
    }
}
