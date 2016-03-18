package com.excilys.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The Class User.
 */
@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "username", unique = true, nullable = false, length = 45)
  private String        username;

  @Column(name = "passwd", nullable = false, length = 60)
  private String        passwd;

  @Column(name = "enabled", nullable = false)
  private boolean       enabled;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private Set<UserRole> userRole = new HashSet<UserRole>(0);

  /**
   * Instantiates a new user.
   */
  public User() {
  }

  /**
   * Instantiates a new user.
   *
   * @param username the username
   * @param passwd the password
   * @param enabled the enabled
   */
  public User(String username, String passwd, boolean enabled) {
    this.username = username;
    this.passwd = passwd;
    this.enabled = enabled;
  }

  /**
   * Instantiates a new user.
   *
   * @param username the username
   * @param passwd the password
   * @param enabled the enabled
   * @param userRole the user role
   */
  public User(String username, String passwd, boolean enabled, Set<UserRole> userRole) {
    this.username = username;
    this.passwd = passwd;
    this.enabled = enabled;
    this.userRole = userRole;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswd() {
    return this.passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Set<UserRole> getUserRole() {
    return this.userRole;
  }

  public void setUserRole(Set<UserRole> userRole) {
    this.userRole = userRole;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (enabled ? 1231 : 1237);
    result = prime * result + ((passwd == null) ? 0 : passwd.hashCode());
    result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    User other = (User) obj;
    if (enabled != other.enabled) {
      return false;
    }
    if (passwd == null) {
      if (other.passwd != null) {
        return false;
      }
    } else if (!passwd.equals(other.passwd)) {
      return false;
    }
    if (userRole == null) {
      if (other.userRole != null) {
        return false;
      }
    } else if (!userRole.equals(other.userRole)) {
      return false;
    }
    if (username == null) {
      if (other.username != null) {
        return false;
      }
    } else if (!username.equals(other.username)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "User [username=" + username + ", password=" + passwd + ", enabled=" + enabled
        + ", userRole=" + userRole + "]";
  }

}