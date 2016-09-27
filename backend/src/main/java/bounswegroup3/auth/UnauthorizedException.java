package bounswegroup3.auth;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends WebApplicationException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException() {
        this("Error authenticating user", "default-realm");
    }

    public UnauthorizedException(String message, String realm) {
        super(Response.status(Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=" + realm).build());
    }
}
