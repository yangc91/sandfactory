package com.yc.sandfactory.service;

import com.yc.sandfactory.entity.SystemLog;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.util.Constants;

/**
 * 日志
 * @project: sandfactory
 * @author: yc
 */
public interface ISystemLogService {

  /**
   * 分页查找
   * @param pageNo
   * @param pageSize
   * @return
   */
  LitePaging<SystemLog> queryForPage(String startTime, String endTime, String action,  String search, Integer pageNo, Integer pageSize);

  /**
   * 记录日志
   * @param logType
   * @param content
   */
  void addLog(String ip, Constants.ENUM_LOG_TYPE logType, String content);

}
