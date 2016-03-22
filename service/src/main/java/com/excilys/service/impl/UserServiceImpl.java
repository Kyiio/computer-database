package com.excilys.service.impl;

import com.excilys.dao.UserDao;
import com.excilys.model.UserRole;
import com.excilys.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  @Autowired
  private UserDao             userDao;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    LOGGER.info("Security : loadUserByUsername : " + username);

    com.excilys.model.User user = userDao.getByName(username);

    List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

    return buildUserForAuthentication(user, authorities);
  }

  /**
   * Builds the user for authentication.
   *
   * @param user the user
   * @param authorities the authorities
   * @return the user
   */
  private User buildUserForAuthentication(com.excilys.model.User user,
      List<GrantedAuthority> authorities) {

    LOGGER.info(
        "Security : buildUserForAuthentication: " + user + " with authorities : " + authorities);

    return new User(user.getUsername(), user.getPasswd(), user.isEnabled(), true, true, true,
        authorities);
  }

  private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

    LOGGER.info("buildUserAuthority : " + userRoles);

    Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

    // Build user's authorities
    for (UserRole userRole : userRoles) {
      setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
    }

    List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

    return result;
  }

}
