package mk.ukim.finki.kiii.kiiiproject.model;

import org.springframework.security.core.GrantedAuthority;

public enum StudentType implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_REGULAR;

    @Override
    public String getAuthority() {
        return name();
    }
}
