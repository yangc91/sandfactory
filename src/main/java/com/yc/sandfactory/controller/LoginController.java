package com.yc.sandfactory.controller;

import com.yc.sandfactory.bean.BaseResponse;
import com.yc.sandfactory.entity.User;
import com.yc.sandfactory.service.ISystemLogService;
import com.yc.sandfactory.service.IUserService;
import com.yc.sandfactory.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: yc
 * @date: 2018-5-20.
 */
@RestController
public class LoginController extends BaseControl {

  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IUserService userService;

  @Autowired
  private ISystemLogService systemLogService;

  @PostMapping("login")
  public BaseResponse login(String username, String password, HttpServletRequest request) {
    if (StringUtils.isBlank(username)) {
      return this.returnError("用户名不能为空");
    }

    if (StringUtils.isBlank(password)) {
      return this.returnError("密码不能为空");
    }

    try {
      UsernamePasswordToken token = new UsernamePasswordToken(username, password);
      token.setRememberMe(true);
      SecurityUtils.getSubject().login(token);
    } catch (LockedAccountException e) {
      logger.error("用户被停用", e);
      return this.returnError("用户被停用");
    } catch (UnknownAccountException e) {
      logger.error("未知用户信息", e);
      return this.returnError("未知用户信息");
    } catch (AuthenticationException e) {
      logger.error("用户名或密码错误", e);
      return this.returnError("用户名或密码错误");
    }

//    User user = userService.getUserByUserName(username);
    systemLogService.addLog(request.getRemoteAddr(), Constants.ENUM_LOG_TYPE.loginLog, "【"+username+"】登录成功");
    return this.returnSuccess(SUCCESS);

  }

  @GetMapping("logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    try {
      User user = (User) SecurityUtils.getSubject().getSession().getAttribute("USER_INFO");
      systemLogService.addLog(request.getRemoteAddr(), Constants.ENUM_LOG_TYPE.logoutLog,
          "【" + user.getUsername() + "】注销成功");
    } catch (Exception e) {
      logger.error("记录退出日志出现异常", e);
    }
    SecurityUtils.getSubject().logout();
    try {
      response.sendRedirect("/login.html");
    } catch (IOException e) {
      logger.error("退出登录失败", e);
    }
  }
}
