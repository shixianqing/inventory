package com.inventory.inventory.common.exception;

/**
 * @Author:shixianqing
 * @Date:2019/1/1411:15
 * @Description: 业务异常类
 **/
public class BusinessException extends RuntimeException {

    private Integer errorCode;//错误码

    private String errorMsg;//错误原因

    public BusinessException(Integer errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
