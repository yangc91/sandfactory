package com.yc.sandfactory.service;

import com.yc.sandfactory.entity.SysRole;
import com.yc.sandfactory.page.LitePaging;

/**
 * TODO hsun 完成注释
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/19 下午11:10
 */
public interface ISysRoleService {

    /**
     * 添加系统角色
     * @param sysRole
     */
    void addSysRole(SysRole sysRole);

    /**
     * 分页查询用户角色
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    LitePaging<SysRole> queryForPage(String name, int pageNo, int pageSize);
}
