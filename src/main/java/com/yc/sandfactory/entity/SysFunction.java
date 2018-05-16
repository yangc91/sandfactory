package com.yc.sandfactory.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import java.io.Serializable;

/**
 * 系统菜单
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/16 下午10:50
 */
@Table("t_sys_func")
public class SysFunction implements Serializable {

    private static final long serialVersionUID = 7075836061738470378L;

    @Id
    @Column("n_id")
    private Long id;

    /**
     * 名称
     */
    @Column("c_name")
    private String name;

    /**
     * 图标
     */
    @Column("c_icon")
    private String icon;

    /**
     * 上级菜单ID
     */
    @Column("n_parent_id")
    private Long parentId;

    /**
     * 链接地址
     */
    @Column("c_link")
    private String link;

    /**
     * 排序
     */
    @Column("n_sort")
    private int sort = 999;

    /**
     * 状态
     * 0-停用 1-启用
     */
    @Column("n_status")
    private Integer status;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
