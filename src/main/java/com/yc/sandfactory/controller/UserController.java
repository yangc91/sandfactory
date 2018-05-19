package com.yc.sandfactory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yc.sandfactory.entity.SysRole;
import com.yc.sandfactory.entity.User;
import com.yc.sandfactory.page.DataTablesParameters;
import com.yc.sandfactory.page.DataTablesReply;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.page.Pagination;
import com.yc.sandfactory.service.ISystemLogService;
import com.yc.sandfactory.service.IUserService;
import com.yc.sandfactory.util.Constants;
import com.yc.sandfactory.util.JsonMapperProvide;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO hsun 完成注释
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/17 上午12:18
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUserService userService;

    @Autowired
    private ISystemLogService systemLogService;

    //@RequestMapping("list")
    //public Pagination list() {
    //    List<SysRole> roles = new ArrayList<>();
    //    for (int i = 1; i < 11; i++) {
    //        SysRole sysRole = new SysRole();
    //        sysRole.setId((long) i);
    //        sysRole.setName("role-" + i);
    //        sysRole.setNote("note-" + i);
    //
    //        roles.add(sysRole);
    //    }
    //
    //    return new Pagination(0, 10, 100, roles);
    //}

    @RequestMapping(value = "/list")
    public Object list(User condition)
        throws JsonProcessingException {

        logger.info("user list : 参数：condition：{}",
            JsonMapperProvide.alwaysMapper().writeValueAsString(condition)
        );

        DataTablesParameters tables = DataTablesParameters.newInstance();

        LitePaging<User> pagination =
            userService.queryUserForPage(condition,
                tables.getPage(), tables.getLength());

        return new DataTablesReply(pagination, tables.getDraw());
    }

    @RequestMapping(value = "/get")
    public Object get(Integer id) {
        logger.info("user get：id：{}", id);
        User user = userService.getUser(id);
        return user;
    }

    @RequestMapping(value = "/del")
    public Object del(Integer id, HttpServletRequest request) {
        logger.info("user del：id：{}", id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", "false");

        try {
            userService.delUser(id);
            result.put("success", "true");
            systemLogService.addLog(request.getRemoteAddr(), Constants.ENUM_LOG_TYPE.userManagerLog, "删除用户【 " +id+ "】成功");
        } catch (Exception e) {
            logger.error("删除用户出错", e);
        }

        return result;
    }

    @RequestMapping(value = "/add")
    public Object get(User user, HttpServletRequest request) throws JsonProcessingException {
        logger.info("user add：输入参数：{}", JsonMapperProvide.alwaysMapper().writeValueAsString(user));
        Map<String, Object> result = new HashMap<>();
        result.put("success", "false");
        try {
            userService.addUser(user);
            systemLogService.addLog(request.getRemoteAddr(), Constants.ENUM_LOG_TYPE.userManagerLog, "添加用户【 " +user.getName()+ "】成功");
        } catch (Exception e) {
            logger.error("添加用户出错", e);
        }

        return user;
    }

    @RequestMapping(value = "/update")
    public Object update(User user, HttpServletRequest request) throws JsonProcessingException {

        logger.info("user update：输入参数：{}",
            JsonMapperProvide.alwaysMapper().writeValueAsString(user));
        Map<String, Object> result = new HashMap<>();
        result.put("success", "false");

        try {
            userService.updateUser(user);
            result.put("success", "true");
            systemLogService.addLog(request.getRemoteAddr(), Constants.ENUM_LOG_TYPE.userManagerLog, "修改用户【 " +user.getName()+ "】成功");
        } catch (Exception e) {
            logger.error("更新用户出错", e);
        }
        return result;
    }

}
