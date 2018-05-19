package com.yc.sandfactory.controller;

import com.yc.sandfactory.entity.SysRole;
import com.yc.sandfactory.entity.SystemLog;
import com.yc.sandfactory.page.DataTablesParameters;
import com.yc.sandfactory.page.DataTablesReply;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO hsun 完成注释
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/19 下午11:14
 */
@RestController
@RequestMapping("/admin/sys/role/")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @PostMapping("save")
    public Object save(SysRole sysRole) {
        this.sysRoleService.addSysRole(sysRole);
        return null;
    }

    @PostMapping("list")
    public Object list(String name) {
        DataTablesParameters tables = DataTablesParameters.newInstance();

        LitePaging<SysRole> pagination = this.sysRoleService.queryForPage(name,tables.getPage(), tables.getLength());

        return new DataTablesReply(pagination, tables.getDraw());
    }

}
