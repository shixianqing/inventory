package com.inventory.inventory.common.enums;

public enum TaskStatus {

    WAITING("待处理","0"),
    ASSIGNED("已分配","1"),
    PROCESSING("处理中","2"),
    COMPLETED("已完成","3");

   private String name;
   private String status;

   TaskStatus(String name,String status){
       this.name = name;
       this.status = status;
   }

   public String getStatus(){
       return this.status;
   }

}
