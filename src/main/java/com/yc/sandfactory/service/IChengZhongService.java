package com.yc.sandfactory.service;

import com.yc.sandfactory.entity.ChengZhongRecord;
import com.yc.sandfactory.page.LitePaging;

/**
 * @project: sandfactory
 * @author: yc
 */
public interface IChengZhongService {

  /**
   * 分页查找
   * @param searchKey
   * @param pageSize
   * @param pageNo
   * @return
   */
  LitePaging<ChengZhongRecord> queryRecordForPage(String searchKey, Integer pageSize,
      Integer pageNo);
}
