package com.mos.global.config.db;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
@Order(1)
public class DataSourceAspect {
  @Pointcut("execution(* com.mos..*(..)) && @annotation(transactional)")
  public void transactionalOperation(Transactional transactional) {
  }


  @Before(value = "transactionalOperation(transactional)", argNames = "transactional")
  public void aroundTransactionalOperation(Transactional transactional) {
      if (transactional.readOnly()) {
        RoutingDataSourceManager.setCurrentDataSourceName(DataSourceType.SLAVE);
      } else {
        RoutingDataSourceManager.setCurrentDataSourceName(DataSourceType.MASTER);
      }
  }
}
