package com.mos.global.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
  private static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContext.context = applicationContext;
  }

  public static Object getBean(String beanName) {
    return context.getBean(beanName);
  }

  public static <T> T getBean(Class<T> beanType) {
    return context.getBean(beanType);
  }
}
