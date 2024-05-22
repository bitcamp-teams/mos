package com.mos.domain.code.service.impl;

import com.mos.domain.code.dto.CodeRequestDto;
import com.mos.domain.code.entity.Code;
import com.mos.domain.code.entity.CodeGroup;
import com.mos.domain.code.repository.CodeRepository;
import com.mos.global.common.paging.Paging;
import com.mos.global.config.db.DataSourceConfig;
import com.mos.global.config.db.DataSourceType;
import com.mos.global.config.db.RoutingDataSourceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
@Import(DataSourceConfig.class)
public class DefaultCodeServiceTest {

  @InjectMocks
  private DefaultCodeService defaultCodeService;

  @Mock
  private CodeRepository codeRepository;


  @Test
  @Transactional
  @DisplayName("insert 쿼리는 master DB 로 날라간다.")
  void testMasterDataSourceForAdd() {
    RoutingDataSourceManager.setCurrentDataSourceName(DataSourceType.MASTER);

    // given
    CodeRequestDto codeRequestDto = CodeRequestDto.builder().codeGroup(
            CodeGroup.builder().codeGroup("codeGroup11").codeGroupName("codeGroupName11")
                .moduleCode("moduleCode11").createDate(Date.valueOf(LocalDate.now())).build())
        .code("code11").build();

    // when
    codeRepository.add(codeRequestDto.toEntity());

    // then
    // master db에 쿼리가 날아갔는지 검증
    assertThat(currentDataSource()).isEqualToIgnoringCase("master");
  }

  @Test
  @Transactional
  @DisplayName("update 쿼리는 master DB 로 날라간다.")
  void testMasterDataSourceForUpdate() {
    RoutingDataSourceManager.setCurrentDataSourceName(DataSourceType.MASTER);

    // given
    CodeRequestDto codeRequestDto = CodeRequestDto.builder().codeGroup(
            CodeGroup.builder().codeGroup("codeGroup22").codeGroupName("codeGroupName22")
                .moduleCode("moduleCode22").createDate(Date.valueOf(LocalDate.now())).build())
        .code("code22").build();

    // when
    codeRepository.update(codeRequestDto.toEntity());

    // then
    // master db에 쿼리가 날아갔는지 검증
    assertThat(currentDataSource()).isEqualToIgnoringCase("master");
  }

  @Test
  @Transactional
  @DisplayName("delete 쿼리는 master DB 로 날라간다.")
  void testMasterDataSourceForDelete() {
    RoutingDataSourceManager.setCurrentDataSourceName(DataSourceType.MASTER);

    // given when
    codeRepository.delete(Code.builder().code("W02-104").build());

    // then
    // master db에 쿼리가 날아갔는지 검증
    assertThat(currentDataSource()).isEqualToIgnoringCase("master");
  }



  @Test
  @Transactional(readOnly = true)
  @DisplayName("select 쿼리는 slave DB 로 날라간다.")
  void testSlaveDataSourceForList() {
    RoutingDataSourceManager.setCurrentDataSourceName(DataSourceType.SLAVE);

    // given
    Paging paging = Paging.builder().pageNo(1).pageSize(10).numOfRecord(30).build();

    // when
    defaultCodeService.listGroup(paging);

    // then
    // slave db에 쿼리가 날아갔는지 검증
    assertThat(currentDataSource()).isEqualToIgnoringCase("slave");
  }

  private String currentDataSource() {
    // 현재 설정된 데이터소스 키 반환
    return RoutingDataSourceManager.getCurrentDataSourceName().name();
  }
}
