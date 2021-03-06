package com.inventory.inventory.common.response;

/**
 * 前后端交互的状态码.
 *
 */
public final class ResponseCode {

    private ResponseCode() {
    }

    /**
     * 成功状态码,在返回对象的meta.
     */
    public static final Integer SUCCESS = 200;

    /**
     * 系统未知异常提示信息.
     */
    public static final String SYSTEM_ERROR_MSG = "系统繁忙，请稍后再试.";

    /**
     * 错误状态码,在返回对象的meta.
     */
    public static final Integer ERROR = 500;

    /**
     * 错误状态码,在返回对象的meta.
     */
    public static final Integer UNKNOW_ERROR = 500;

    /**
     * 请求参数错误统一状态码.
     */
    public static final Integer BAD_REQUEST = 400;

    /**
     * 没有认证通过， 需要重新登录.
     */
    public static final Integer UNAUTHORIZED = 401;

    /**
     * 没有权限， 需要重新登录.
     */
    public static final Integer FORBIDDEN = 403;

    /**
     * 空参数
     */
    public static final Integer PARAM_EMPTY_CODE = 9999;

    /**
     * 新增失败
     */
    public static final Integer INSERT_FAIL_CODE = 4444;

    /**
     * 修改失败码
     */
    public static final Integer UPDATE_FAIL_CODE = 5555;


    public static final Integer CHECK_FAIL_CODE = 8888;

    /**
     * 密码不等
     */
    public static final Integer PWD_NOT_EQUAL_CODE = 2222;

    /**
     * 用户不存在
     */
    public static final Integer USER_NOT_EXSIT_CODE = 3333;

    /**
     * 用户已存在
     */
    public static final Integer USER_EXSIT_CODE = 12312;

    /**
     * 密码错误
     */
    public static final Integer PWD_ERROR_CODE = 6666;

    /**
     * 文件上传失败码
     */
    public static final Integer UPLOAD_FILE_FAIL_CODE = 9212;
}
