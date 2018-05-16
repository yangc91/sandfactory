package com.yc.sandfactory.service;

import com.yc.sandfactory.bean.RecodRequestBean;
import com.yc.sandfactory.entity.ChengZhongRecord;
import com.yc.sandfactory.page.LitePaging;

/**
 * @project: sandfactory
 * @author: yc
 */
public interface IChengZhongService {

  /**
   * 分页查找
   * @param recodRequestBean
   * @param pageNo
   * @param pageSize
   * @return
   */
  LitePaging<ChengZhongRecord> queryRecordForPage(RecodRequestBean recodRequestBean, Integer pageNo, Integer pageSize);

  ChengZhongRecord getRecord(int id);


}
