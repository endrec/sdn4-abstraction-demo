package com.example.demo.security.domain;

import com.example.demo.domain.UserRole;
import com.example.demo.domain.node.User;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    @Delegate
    private final User user;

    @RequiredArgsConstructor
    private static class AuthRole implements GrantedAuthority {
        private static final long serialVersionUID = 1L;

        private final UserRole role;

        @Override
        public String getAuthority() {
            return role.toString();
        }

        @Override
        public String toString() {
            return role.toString();
        }
    }

    public AuthUser(final User user) {
        super(user.getEmail(), user.getPassword(), getAuthorityRoles(user.getRoles()));
        this.user = user;
    }

    private static Collection<GrantedAuthority> getAuthorityRoles(final Collection<UserRole> roles) {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> authorities.add(new AuthRole(role)));
        return authorities;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
}
