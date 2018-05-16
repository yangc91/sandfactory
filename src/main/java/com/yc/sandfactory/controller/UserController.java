package com.yc.sandfactory.controller;

import com.yc.sandfactory.entity.SysRole;
import com.yc.sandfactory.page.Pagination;
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

    @RequestMapping("list")
    public Pagination list() {
        List<SysRole> roles = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            SysRole sysRole = new SysRole();
            sysRole.setId((long) i);
            sysRole.setName("role-" + i);
            sysRole.setNote("note-" + i);

            roles.add(sysRole);
        }

        return new Pagination(0, 10, 100, roles);
    }
}
