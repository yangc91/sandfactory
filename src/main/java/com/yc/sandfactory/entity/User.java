package com.yc.sandfactory.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;

/**
 * 用户
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/16 下午10:24
 */
@Table("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 2102264140761200648L;

    @Id
    @Column("n_id")
    private Long id;

    /**
     * 名称
     */
    @Column("c_name")
    private String name;

    /**
     * 职位
     */
    @Column("c_position")
    private String position;

    /**
     * 排序
     */
    @Column("n_sort")
    private int sort = 999;

    /**
     * 电话
     */
    @Column("c_tel")
    private String tel;

    /**
     * 手机号
     */
    @Column("c_mobile_tel")
    private String mobileTel;

    /**
     * 邮箱
     */
    @Column("c_email")
    private String email;

    /**
     * 添加时间
     */
    @Column("n_create_time")
    private long createTime = System.currentTimeMillis();

    /**
     * 状态
     */
    @Column("n_status")
    private int status;

    /**
     * 修改时间
     */
    @Column("n_update_time")
    private Long updateTime;

    /**
     * 性別0女1男
     */
    @Column("n_sex")
    private Integer sex = 1;

    /**
     * 所属部门ID
     */
    @Column("n_dept_id")
    private Long deptId;

    /**
     * 所属角色
     */
    @Column("n_role_id")
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobileTel() {
        return mobileTel;
    }

    public void setMobileTel(String mobileTel) {
        this.mobileTel = mobileTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
