package com.excilys.utils;

import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;

public class MockitoFactoryBean<T> implements FactoryBean<T> {

  private Class<T> classToMock;

  /**
   * Creates a Mockito mock instance of the provided class.
   * 
   * @param classToMock The class to be mocked.
   */
  public MockitoFactoryBean(Class<T> classToMock) {
    this.classToMock = classToMock;
  }

  @Override
  public T getObject() throws Exception {
    return Mockito.mock(classToMock);
  }

  @Override
  public Class<?> getObjectType() {
    return classToMock;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}