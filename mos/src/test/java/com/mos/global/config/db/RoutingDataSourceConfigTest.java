package com.mos.global.config.db;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;


@Slf4j
@SpringBootTest
public class RoutingDataSourceConfigTest {
  private static final String DETERMINE_CURRENT_LOOKUP_KEY = "determineCurrentLookupKey";

  @Transactional(readOnly = false)
  @DisplayName("MasterDataSource Replication 설정 테스트")
  @Test
  void testMasterDataSourceReplication() throws Exception {

    // Given
    RoutingDataSource routingDataSource = new RoutingDataSource();

    // When
    Method declaredMethod = RoutingDataSource.class.getDeclaredMethod(DETERMINE_CURRENT_LOOKUP_KEY);
    declaredMethod.setAccessible(true);

    Object object = declaredMethod.invoke(routingDataSource);

    // Then
    log.info("object : [{}]", object);
    Assertions.assertEquals(DataSourceType.MASTER.toString(), object.toString());
  }

  @Transactional(readOnly = true)
  @DisplayName("SlaveDataSource Replication 설정 테스트")
  @Test
  void testSlaveDataSourceReplication() throws Exception {

    // Given
    RoutingDataSource routingDataSource = new RoutingDataSource();

    // When
    Method declaredMethod = RoutingDataSource.class.getDeclaredMethod(DETERMINE_CURRENT_LOOKUP_KEY);
    declaredMethod.setAccessible(true);

    Object object = declaredMethod.invoke(routingDataSource);

    // Then
    log.info("object : [{}]", object);
    Assertions.assertEquals(DataSourceType.SLAVE.toString(), object.toString());
  }
}
