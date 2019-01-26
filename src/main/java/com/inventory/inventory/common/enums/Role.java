package com.inventory.inventory.common.enums;

public enum Role {
    ADMIN("管理员",1),DELIVERY_MAIN("送货员",2),DEALER("经销商",3);

    private String name;
    private int type;

    Role(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
