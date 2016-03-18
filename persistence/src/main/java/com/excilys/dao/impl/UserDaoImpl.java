package com.excilys.dao.impl;

import com.excilys.dao.UserDao;
import com.excilys.model.User;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userDaoImpl")
public class UserDaoImpl implements UserDao {

  private static final Logger LOGGER      = LoggerFactory.getLogger(UserDaoImpl.class);

  private static final String GET_BY_NAME =
      "SELECT user FROM User AS user WHERE user.username = :username";

  @Autowired
  private SessionFactory      sessionFactory;

  @Override
  public User getByName(String username) {

    LOGGER.info("Get user by username : " + username);

    Session session = sessionFactory.getCurrentSession();
    Query query = session.createQuery(GET_BY_NAME);
    query.setString("username", username);
    LOGGER.error("Query " + query);
    User user = (User) query.uniqueResult();

    return user;
  }

}
