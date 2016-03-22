package com.excilys.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.excilys.dao.UserDao;
import com.excilys.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./persistence-context-test.xml" })
@Transactional(transactionManager = "txManager")
@Rollback(true)
public class UserDaoImplTest {

  @Resource(name = "userDaoImpl")
  private UserDao userDao;

  @Test
  public void testGetByNameNull() {
    User user = userDao.getByName(null);
    assertNull(user);
  }

  @Test
  public void testGetByName() {
    User user = userDao.getByName("admin");
    assertEquals(user.getUsername(), "admin");
  }
}
