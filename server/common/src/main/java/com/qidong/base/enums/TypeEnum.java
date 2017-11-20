package com.qidong.base.enums;

public enum TypeEnum {
    TYPE_USER("user","用户"),TYPE_RETRAUTANT("retrautant","餐饮"),TYPE_HAIRCUT("haircut","理发");
    private String name;
    private String desc;

    TypeEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
