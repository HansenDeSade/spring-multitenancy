package de.hansendesade.multitenancy.model;

public class ApplicationUser {

  private String username;
  private String password;

  private ApplicationRole role;

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

  public ApplicationRole getRoles() {
    return role;
  }

  public void setRoles(ApplicationRole role) {
    this.role = role;
  }

}
