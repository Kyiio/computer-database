package com.excilys.dao;

import com.excilys.model.User;

public interface UserDao {

  /**
   * Find by user name.
   *
   * @param username the username
   * @return the user
   */
  public User getByName(String username);
  
}
