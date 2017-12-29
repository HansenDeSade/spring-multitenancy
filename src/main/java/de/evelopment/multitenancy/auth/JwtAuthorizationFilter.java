package de.evelopment.multitenancy.auth;

import de.evelopment.multitenancy.util.ErrorMessage;
import de.evelopment.multitenancy.util.ObjectMapperResolver;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

  private UserDetailsService userDetailsService;
  private String securityJwtSecret = JwtSecurityConstants.SECRET;

  public JwtAuthorizationFilter(UserDetailsService userDetailsService, String securityJwtSecret) {
    this.userDetailsService = userDetailsService;
    this.securityJwtSecret = securityJwtSecret;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    String header = req.getHeader(JwtSecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(JwtSecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }
    UsernamePasswordAuthenticationToken authentication = null;
    try {
      authentication = getAuthentication(header);
    } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e) {
      LOG.error("failed authorizing user or jwt", e);
      res.reset();
      res.setStatus(HttpStatus.UNAUTHORIZED.value());
      MediaType mediaType = ObjectMapperResolver.getMediaType(req.getHeader(HttpHeaders.ACCEPT));
      res.setContentType(mediaType.toString());
      ErrorMessage errorMessage = new ErrorMessage(e.getMessage(), e, false);
      errorMessage.setPathByRequest(req);
      res.getWriter().write(ObjectMapperResolver.getObjectMapper(mediaType.toString()).writeValueAsString(errorMessage));
      return;
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(String headerToken) {
    String user = Jwts.parser()
        .setSigningKey(Base64.getEncoder().encodeToString(securityJwtSecret.getBytes(Charset.defaultCharset())))
        .parseClaimsJws(headerToken.replace(JwtSecurityConstants.TOKEN_PREFIX, "").trim())
        .getBody()
        .getSubject();

    if (user != null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(user);
      return new UsernamePasswordAuthenticationToken(user, userDetails.getPassword(), userDetails.getAuthorities());
    }
    throw new IllegalArgumentException("no subject specified");
  }
}
