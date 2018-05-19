package com.yc.sandfactory.util;

/**
 * @author: yc
 * @date: 2018-5-19.
 */
public class Constants {

  public enum ENUM_LOG_TYPE {
    /**
     * 登录
     */
    loginLog(1),
    /**
     * 退出
     */
    logoutLog(2),
    /**
     * 运行日志类型
     */
    userManagerLog(3),
    /**
     * 安全日志类型
     */
    roleManagerLog(4);


    public Integer value;

    private ENUM_LOG_TYPE(Integer value) {
      this.value = value;
    }
  }
}
