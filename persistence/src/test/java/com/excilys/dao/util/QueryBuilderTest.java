package com.excilys.dao.util;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:./persistence-context-test.xml" })
public class QueryBuilderTest {

  private QueryBuilder queryBuilder;

  @Before
  public void beforeTest() {
    queryBuilder = new QueryBuilder();
  }

  @After
  public void afterTest() {
    queryBuilder = null;
  }

  @Test
  public void testSelect() {
    queryBuilder.select("*");
    assertEquals(queryBuilder.getQuery(), "SELECT * ");
  }

  @Test
  public void testFrom() {
    queryBuilder.from("table");
    assertEquals(queryBuilder.getQuery(), "FROM table ");
  }

  @Test
  public void testDeleteFrom() {
    queryBuilder.deleteFrom("table");
    assertEquals(queryBuilder.getQuery(), "DELETE FROM table ");
  }

  @Test
  public void testJoinOn() {
    queryBuilder.leftJoin("table", "attribute");
    assertEquals(queryBuilder.getQuery(), "LEFT JOIN table ON attribute ");
  }

  @Test
  public void testWhere() {
    queryBuilder.where("attr = value");
    assertEquals(queryBuilder.getQuery(), "WHERE attr = value ");
  }

  @Test
  public void testOrderBy() {
    queryBuilder.orderBy("by1", "order1");
    assertEquals(queryBuilder.getQuery(), "ORDER BY by1 order1 ");
  }

  @Test
  public void testOffset() {
    queryBuilder.offset("offset");
    assertEquals(queryBuilder.getQuery(), "OFFSET offset ");
  }

  @Test
  public void testLimit() {
    queryBuilder.limit("limit");
    assertEquals(queryBuilder.getQuery(), "LIMIT limit ");
  }

  @Test
  public void appendStr() {
    queryBuilder.append("my string").append("next part");
    assertEquals(queryBuilder.getQuery(), "my string next part ");
  }

  @Test
  public void appendInt() {
    queryBuilder.append(1250);
    assertEquals(queryBuilder.getQuery(), "1250 ");
  }

  @Test
  public void appendLong() {
    queryBuilder.append(1250L);
    assertEquals(queryBuilder.getQuery(), "1250 ");
  }
}
