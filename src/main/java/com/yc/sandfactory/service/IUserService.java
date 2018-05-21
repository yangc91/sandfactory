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
  LitePaging<User> queryUserForPage(User user, Integer pageNo, Integer pageSize);

  /**
   * 根据ID获取用户
   * @param id
   * @return
   */
  User getUser(long id);

  User getUserByUserName(String name);

  void delUser(long id);

  User addUser(User user);

  void updateUser(User user);

}
