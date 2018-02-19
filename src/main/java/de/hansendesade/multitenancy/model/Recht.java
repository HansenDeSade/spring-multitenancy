package de.hansendesade.multitenancy.model;

public enum Recht {
  ADMIN("ADMIN");

  private final String authority;

  Recht(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return authority;
  }
}
