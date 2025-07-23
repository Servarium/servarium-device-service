package app.servarium.adapter.restapi.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthentication extends AbstractAuthenticationToken {
    private final Long principal;

    public JwtAuthentication(Long principal, String role) {
        super(AuthorityUtils.createAuthorityList(role));
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Long getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new UnsupportedOperationException("Can't touch this!");
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof JwtAuthentication that)) return false;
        if (!super.equals(o)) return false;

        return principal.equals(that.principal);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + principal.hashCode();
        return result;
    }
}
