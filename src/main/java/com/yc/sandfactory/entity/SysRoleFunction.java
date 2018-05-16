package com.yc.sandfactory.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;

/**
 * 角色菜单关系
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/16 下午10:50
 */
@Table("t_sys_role_func")
public class SysRoleFunction implements Serializable {

    private static final long serialVersionUID = -8485584444533062370L;

    @Id
    @Column("n_id")
    private Long id;

    /**
     * 角色ID
     */
    @Column("n_role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @Column("n_func_id")
    private Long funcId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getFuncId() {
        return funcId;
    }

    public void setFuncId(Long funcId) {
        this.funcId = funcId;
    }
}
