package com.yc.sandfactory.entity;

import java.io.Serializable;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 类描述：后台日志
 * @author yangchun
 * @date 2016/12/5 10:24
 */
@Table("t_system_log")
public class SystemLog implements Serializable {

    @Id
    @Column("n_id")
    private Long id;

    /**
     * 管理员标识:后台管理员的ID
     */
    @Column("c_manager_id")
    private Long managerId;

    /**
     * 管理员名称:后台管理员的账户名
     */
    @Column("c_manager_name")
    private String managerName;

    /**
     * 管理员请求IP
     */
    @Column("c_manager_ip")
    private String managerIp;

    /**
     * 日志内容
     */
    @Column("c_content")
    private String content;

    /**
     * 日志行为:登录退出：1-登录 2-退出 操作日志：3-人员管理 4-角色管理
     */
    @Column("c_action")
    private String action;

    /**
     * 日志发生时间:毫秒值
     */
    @Column("n_time")
    private Long time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerIp() {
        return managerIp;
    }

    public void setManagerIp(String managerIp) {
        this.managerIp = managerIp;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
