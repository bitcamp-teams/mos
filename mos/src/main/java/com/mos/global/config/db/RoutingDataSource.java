package com.mos.global.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;


public class RoutingDataSource extends AbstractRoutingDataSource {

  @Override
  protected Object determineCurrentLookupKey() {
    DataSourceType dataSourceType = RoutingDataSourceManager.getCurrentDataSourceName();
    if (TransactionSynchronizationManager.isActualTransactionActive()) {
      boolean readOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
      if (readOnly) {
        dataSourceType = DataSourceType.SLAVE;
      } else {
        dataSourceType = DataSourceType.MASTER;
      }
    }

    RoutingDataSourceManager.removeCurrentDataSourceName();
    return dataSourceType;

  }

}
