package de.evelopment.multitenancy.auth;

import de.evelopment.multitenancy.model.Recht;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.AnnotationMetadataExtractor;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends GlobalMethodSecurityConfiguration {

  @Bean
  public AnonymousAuthenticationFilter anonymousAuthenticationFilter() {
    return new AnonymousAuthenticationFilter("anonymous", "anonymous", AuthorityUtils.createAuthorityList("ANONYMOUS"));
  }

  @Bean
  public AuthenticationTrustResolver trustResolver() {
    return new AuthenticationTrustResolverImpl();
  }

  @Value("${security.basic.enabled}")
  private boolean securityEnabled;

  @Override
  protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
    return securityEnabled ? new SecuredAnnotationSecurityMetadataSource(new UserAuthoritySecuredAnnotationMetadataExtractor()) : null;
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  private static class UserAuthoritySecuredAnnotationMetadataExtractor implements AnnotationMetadataExtractor<UserAuthoritySecured> {
    @Override
    public Collection<ConfigAttribute> extractAttributes(final UserAuthoritySecured secured) {
      final Recht[] userAuthorities = secured.value();
      final List<ConfigAttribute> attributes = new ArrayList<>(userAuthorities.length);

      for (final Recht authority : userAuthorities) {
        attributes.add(new org.springframework.security.access.SecurityConfig(authority.getAuthority()));
      }

      return attributes;
    }
  }
}
