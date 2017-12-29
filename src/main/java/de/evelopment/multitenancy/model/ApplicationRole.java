package de.evelopment.multitenancy.model;

import javax.persistence.*;

public class ApplicationRole {

  private int id;

  private String role;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

}
