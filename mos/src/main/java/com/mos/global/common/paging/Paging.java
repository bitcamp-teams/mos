package com.mos.global.common.paging;

import lombok.Builder;
import lombok.Getter;

/**
 * - `pageNo`: 현재 페이지 번호를 나타냅니다. 기본값은 1입니다. - `pageSize`: 페이지당 항목 수를 나타냅니다. 기본값은 20입니다. - `count`:
 * 현재 페이지에 표시되는 항목 수를 나타냅니다. - `numOfRecord`: 전체 레코드 수를 나타냅니다. - `numOfPage`: 전체 페이지 수를 나타냅니다. -
 * `startRow`: 현재 페이지의 첫 번째 행 인덱스를 나타냅니다. - `endRow`: 현재 페이지의 마지막 행 인덱스를 나타냅니다.
 *
 * `calculate()` : 페이지 수, 현재 페이지의 항목 수, 시작 및 끝 행 인덱스 등을 계산
 */
@Getter
public class Paging {
  private int pageNo = 1;
  private int pageSize = 20;
  private int count;
  private int numOfRecord;
  private int numOfPage;
  private int startRow = 0;
  private int endRow = -1;

  @Builder
  public Paging(int pageNo, int pageSize, int numOfRecord) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
    this.numOfRecord = numOfRecord;
    this.count = 0;
    calculate();
  }

  private void calculate() {

    if (pageNo <= 0)
      pageNo = 1;

    if (pageSize > 0) {

      numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

      if (numOfPage == pageNo) {
        count = numOfRecord % pageSize;
        if (numOfRecord == pageSize) {
          count = numOfRecord;
        }
      } else if (numOfPage > pageNo) {
        count = pageSize;
      }

      startRow = pageSize * (pageNo - 1);
      endRow = startRow + pageSize;

    } else {
      pageNo = 1;
      pageSize = numOfRecord;
      count = numOfRecord;
      startRow = 0;
      endRow = numOfRecord - 1;
      numOfPage = 1;
    }
  }
}
