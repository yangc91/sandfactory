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
     * 人员管理
     */
    userManagerLog(3),
    /**
     * 角色管理
     */
    roleManagerLog(4),

    /**
     * 监控管理
     */
    videoManagerLog(5);


    public Integer value;

    private ENUM_LOG_TYPE(Integer value) {
      this.value = value;
    }
  }
}
