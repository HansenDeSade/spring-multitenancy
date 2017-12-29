package de.evelopment.multitenancy.model;

import java.io.Serializable;

public class UserData implements Serializable {

  private static final long serialVersionUID = -4541368085783640262L;

  private String username;
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}