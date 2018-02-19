package de.hansendesade.multitenancy.auth;

public interface JwtSecurityConstants {

  String SECRET = "e64nIEp6k/o3E";
  long DEFAULT_EXPIRATION_TIME = 86_400_000; // 1 day
  String TOKEN_PREFIX = "Bearer";
  String HEADER_STRING = "Authorization";

}
