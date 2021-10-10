package innotutor.innotutor_backend.security;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor
@Component
public class SecurityUtils {

    public String getTokenFromRequest(final HttpServletRequest request) {
        String token = null; //NOPMD - suppressed DataflowAnomalyAnalysis
        final Cookie cookieToken = WebUtils.getCookie(request, "token");
        if (cookieToken == null) {
            final String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                token = bearerToken.substring(7);
            }
        } else {
            token = cookieToken.getValue();
        }
        return token;
    }
}