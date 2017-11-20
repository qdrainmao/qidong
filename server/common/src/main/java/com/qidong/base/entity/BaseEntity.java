package com.qidong.base.entity;

import java.util.Date;

public class BaseEntity {
    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private Short status;

    public void setBaseEntity(String name){
        Date now = new Date();
        createBy = name;
        createTime = now;
        updateBy = name;
        updateTime = now;
        status = 1;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
