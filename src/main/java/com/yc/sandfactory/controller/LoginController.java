package com.yc.sandfactory.controller;

import com.yc.sandfactory.entity.User;
import com.yc.sandfactory.service.ISystemLogService;
import com.yc.sandfactory.service.IUserService;
import com.yc.sandfactory.util.Constants;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: yc
 * @date: 2018-5-20.
 */
@Controller
public class LoginController {

  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IUserService userService;

  @Autowired
  private ISystemLogService systemLogService;

  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  public String login(String userName, String password, ModelMap model, HttpServletRequest request) {
    if (StringUtils.isBlank(userName)) {
      model.addAttribute("message", "用户名不能为空");
    } else if (StringUtils.isBlank(password)) {
      model.addAttribute("message", "密码不能为空");
    } else {
      boolean isLogined = true;
      try {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        SecurityUtils.getSubject().login(token);
      } catch (LockedAccountException e) {
        isLogined = false;
        model.addAttribute("message", "用户被停用");
      } catch (UnknownAccountException e) {
        isLogined = false;
        model.addAttribute("message", "未知用户信息");
      } catch (IncorrectCredentialsException e) {
        isLogined = false;
        model.addAttribute("message", "用户名或密码错误");
      } catch (AuthenticationException e) {
        e.printStackTrace();
        isLogined = false;
        model.addAttribute("message", e.getMessage());
      }

      if (isLogined) {
        User user = userService.getUserByUserName(userName);
        systemLogService.addLog(request.getRemoteAddr(), Constants.ENUM_LOG_TYPE.loginLog, "【"+userName+"】登录成功");
        logger.info("---【{}】登录成功----", userName);
        return "redirect:/page/index.html";
      }
    }
    model.addAttribute("userName", userName);
    return "redirect:/login.html";
  }
}
