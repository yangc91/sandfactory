package com.yc.sandfactory.service;

import com.yc.sandfactory.entity.User;
import com.yc.sandfactory.page.LitePaging;

/**
 * @project: sandfactory
 * @author: yc
 */
public interface IUserService {

  /**
   * 分页查找
   * @param user
   * @param pageNo
   * @param pageSize
   * @return
   */
  LitePaging<User> queryUserForPage(
      User user, Integer pageNo, Integer pageSize);

  User getUser(int id);

  void delUser(int id);

  User addUser(User user);

  void updateUser(User user);

}
