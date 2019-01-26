package com.inventory.inventory.common.enums;

public enum TaskValid {

    VALID("有效","1"), INVALID("无效","0");

    private String name;
    private String isValid;

    TaskValid(String name,String isValid) {
        this.name = name;
        this.isValid = isValid;
    }

    public String getIsValid() {
        return this.isValid;
    }
}
