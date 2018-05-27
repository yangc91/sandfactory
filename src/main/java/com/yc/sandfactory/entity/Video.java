package com.yc.sandfactory.entity;

import java.io.Serializable;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 监控
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/16 下午10:24
 */
@Table("t_video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1451903610767717294L;

    @Id
    @Column("n_id")
    private Long id;

    /**
     * 名称
     */
    @Column("c_name")
    private String name;

    // 沙场
    @Column("c_sand_name")
    private String sandName;


    // 工作站
    @Column("c_work_name")
    private String workName;

    // 地磅
    @Column("c_truck_name")
    private String truckName;

    // 通道号 --- 对应 EasyNvr的通道号
    @Column("n_channel")
    private Integer channel;


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

    public String getSandName() {
        return sandName;
    }

    public void setSandName(String sandName) {
        this.sandName = sandName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}