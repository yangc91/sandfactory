package com.yc.sandfactory.page;

/**
 * @Auther: yangchun
 * @Date: 2017-9-23
 */
public class PageBean {

  private Integer pageNo = 1;

  private Integer pageSize = 10;

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
