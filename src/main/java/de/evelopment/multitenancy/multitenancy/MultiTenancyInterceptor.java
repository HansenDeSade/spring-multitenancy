package de.evelopment.multitenancy.multitenancy;

import de.evelopment.multitenancy.auth.JwtSecurityConstants;
import io.jsonwebtoken.Jwts;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * In order to identify the current identifier we will intercept the incoming request by using a handler interceptor.
 * HandlerInterceptors are somehow similar to Servlet Filters. Spring provides an abstract class
 * (HandlerInterceptorAdapter) which  contains a simplified implementation of the HandlerInterceptor interface for
 * pre-only/post-only interceptors.
 * In our case we are only interested on intercepting the request before it reaches the controller in order to identify
 * the tenant, so we will only override the preHandle method. The tenant ID is going to be a header variable and we are
 * going to retrieve it and add it as an attribute to the incoming request before this is processed by the controller.
 *
 * http://anakiou.blogspot.de/2015/08/multi-tenant-application-with-spring.html
 */
public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

    private String securityJwtSecret = JwtSecurityConstants.SECRET;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {
        //String tenantid = req.getHeader("tenantid");
        String header = req.getHeader(JwtSecurityConstants.HEADER_STRING);
        String tenantid = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(securityJwtSecret.getBytes(Charset.defaultCharset())))
                .parseClaimsJws(header.replace(JwtSecurityConstants.TOKEN_PREFIX, "").trim())
                .getBody()
                .getAudience();
        //Map<String, Object> pathVars = (Map<String, Object>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        //if (pathVars.containsKey("tenantid")) {
        if (tenantid != null && !tenantid.isEmpty()) {
            req.setAttribute("CURRENT_TENANT_IDENTIFIER", tenantid);
        }
        return true;
    }
}
