package de.hansendesade.multitenancy.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hansendesade.multitenancy.model.UserOfTenant;
import de.hansendesade.multitenancy.util.ObjectMapperResolver;
import de.hansendesade.multitenancy.model.UserData;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


  private AuthenticationManager authenticationManager;
  private String securityJwtSecret = JwtSecurityConstants.SECRET;
  private long expirationTime;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String securityJwtSecret, long expirationTime) {
    super(new AntPathRequestMatcher("/authenticate", "POST"));
    this.authenticationManager = authenticationManager;
    this.securityJwtSecret  = securityJwtSecret;
    this.expirationTime = expirationTime;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
    try {
      String contentType = req.getContentType();
      ObjectMapper objectMapper = ObjectMapperResolver.getObjectMapper(contentType);
      UserData creds = objectMapper.readValue(req.getInputStream(), UserData.class);

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
    Date now = new Date();
    Date exp = new Date(now.getTime() + expirationTime);
    String token = Jwts.builder()
        .setSubject(((UserOfTenant) auth.getPrincipal()).getUsername())
        .setExpiration(exp)
        .setIssuer("Multitenancy " + JwtAuthenticationFilter.class.getPackage().getImplementationVersion())
        .setIssuedAt(now)
        .setAudience(((UserOfTenant) auth.getPrincipal()).getTenant())
        .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(securityJwtSecret.getBytes(Charset.defaultCharset())))
        .compact();
    res.addHeader(JwtSecurityConstants.HEADER_STRING, JwtSecurityConstants.TOKEN_PREFIX + " " + token);
    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    res.setHeader("auth-token-expires", sdf.format(exp));
    long epochSecond = exp.toInstant().getEpochSecond();
    res.setHeader("auth-token-expires-epoch", String.valueOf(epochSecond));
    res.setHeader("auth-token-expires-in", String.valueOf(epochSecond - new Date().toInstant().getEpochSecond()));
  }

}