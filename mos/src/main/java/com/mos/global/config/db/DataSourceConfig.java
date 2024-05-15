package com.mos.global.config.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
public class DataSourceConfig {

  @Primary
  @Bean("masterDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
  public DataSource masterDataSource() {
    System.out.println("DataSourceConfig.masterDataSource");
    DataSource build = DataSourceBuilder.create().build();
    System.out.println("build1 = " + build);
    return build;
  }

  @Bean("slaveDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
  public DataSource slaveDataSource() {
    System.out.println("DataSourceConfig.slaveDataSource");
    DataSource build = DataSourceBuilder.create().build();
    System.out.println("build2 = " + build);
    return build;
  }

  @Bean("routingDataSource")
  public DataSource routingDataSource(
      @Qualifier("masterDataSource") final DataSource masterDataSource,
      @Qualifier("slaveDataSource") final DataSource slaveDataSource) {
    final RoutingDataSource routingDataSource = new RoutingDataSource();
    final HashMap<Object, Object> dataSource = new HashMap<>();

    dataSource.put(DataSourceType.MASTER, masterDataSource);
    dataSource.put(DataSourceType.SLAVE, slaveDataSource);

    routingDataSource.setTargetDataSources(dataSource);
    routingDataSource.setDefaultTargetDataSource(masterDataSource);

    return routingDataSource;
  }

  @Bean
  public DataSource lazyRoutingDataSource(
      @Qualifier("routingDataSource") DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

  @Bean
  public PlatformTransactionManager transactionManager(
      @Qualifier(value = "lazyRoutingDataSource") DataSource lazyRoutingDataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(lazyRoutingDataSource);
    return transactionManager;
  }

  //  @Bean
  //  public SqlSessionFactory sqlSessionFactory(
  //      @Qualifier("routingDataSource") DataSource routingDataSource) throws Exception {
  //    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
  //    sessionFactory.setDataSource(routingDataSource);
  //    return sessionFactory.getObject();
  //  }

}
