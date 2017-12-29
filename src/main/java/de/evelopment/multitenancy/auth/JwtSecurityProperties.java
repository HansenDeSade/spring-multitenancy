package de.evelopment.multitenancy.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.jwt")
public class JwtSecurityProperties {

  private String secret;

  private Long expiration;

  public String getSecret() {
    if (secret == null) {
      return JwtSecurityConstants.SECRET;
    }
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public Long getExpiration() {
    if (expiration == null) {
      System.out.println("no jwt expiration time set, using default " + JwtSecurityConstants.DEFAULT_EXPIRATION_TIME);
      return JwtSecurityConstants.DEFAULT_EXPIRATION_TIME;
    }
    return expiration;
  }

  public void setExpiration(Long expiration) {
    this.expiration = expiration;
  }

}
