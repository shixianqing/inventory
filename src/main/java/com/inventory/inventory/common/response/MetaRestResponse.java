package com.inventory.inventory.common.response;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 前后端相应的统一对象.
 *
 */

@SuppressWarnings("serial")
public final class MetaRestResponse extends HashMap<String, Object> {

    private static final String META = "meta";

    private static final String DATA = "data";

    private static final String ERROR = "error";

    private MetaRestResponse() {
        this.put(META, new HashMap<>());
    }

    /**
     * 返回错误.
     *
     * @param errorCode
     *            错误码
     * @param message
     *            错误消息
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private MetaRestResponse errors(int errorCode, String message) {
        if (!this.containsKey(ERROR)) {
            this.put(ERROR, new HashMap<>());
        }
        Map map = (Map) this.get(ERROR);
        map.put("code", errorCode);
        map.put("message", message);
        return this;
    }

    /**
     * 返回给客户端的数据.
     *
     * @param data
     *            可能类型object,list, array, stream
     * @return
     */
    private MetaRestResponse data(Object data) {
        this.put(DATA, data);
        return this;
    }

    /**
     * 返回的meta，有分页.
     *
     * @param code
     *            状态码
     * @param current
     *            当前页
     * @param total
     *            一共多少条
     * @param limit
     *            每页多少条
     *
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private MetaRestResponse pagingMeta(int code, Integer current, Long total, Integer limit) {
        Map map = (Map) this.get(META);
        map.put("code", code);
        return paging(current, total, limit);
    }

    /**
     * 分页.
     *
     * @param current
     *            当前页码
     * @param total
     *            记录条数
     * @param limit
     *            一页记录数
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MetaRestResponse paging(Integer current, Long total, Integer limit) {
        Map map = (Map) this.get(META);
        if (current != null) {
            map.put("current", current);
        }
        if (total != null) {
            map.put("total", total);
        }
        if (limit != null) {
            map.put("limit", limit);
        }

        return this;
    }

    /**
     * 返回的meta，没有分页情况.
     *
     * @param code
     *            状态码
     *
     * @return
     */
    private MetaRestResponse meta(int code) {
        return pagingMeta(code, null, null, null);
    }

    /**
     * 成功返回的结构.
     *
     * @param code
     *            成功状态码，出现在meta
     * @param page
     * @param object
     *            返回前端的业务数据， 可以是一个map或者object
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static MetaRestResponse success(int code, PageInfo page, Object object) {
        MetaRestResponse response = new MetaRestResponse();
        response.pagingMeta(code, page.getPageNum(), page.getTotal(), page.getPageSize());
        response.data(object);
        return response;
    }

    /**
     * 成功返回的结构.
     *
     * @param code
     *            成功状态码，出现在meta
     * @param pageNumber
     *            当前页
     * @param total
     *            一共多少页
     * @param data
     *            返回前端的业务数据, 可以是一个map或者object
     * @return
     */
    public static MetaRestResponse success(int code, Integer pageNumber, Long total, Integer pageSize, Object data) {
        MetaRestResponse response = new MetaRestResponse();
        response.pagingMeta(code, pageNumber, total, pageSize);
        response.data(data);
        return response;
    }

    /**
     * 成功返回的结构.
     *
     * @param code
     *            成功状态码，出现在meta
     * @param data
     *            返回前端的业务数据, 可能类型object,list, array, stream
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static MetaRestResponse success(int code, Object data) {
        MetaRestResponse response = new MetaRestResponse();
        if (data != null && data instanceof PageInfo) {
            PageInfo page = (PageInfo) data;
            response.pagingMeta(code, page.getPageNum(), page.getTotal(), page.getPageSize());
            response.data(page.getList());
        } else {
            response.meta(code);
            response.data(data);
        }
        return response;
    }

    /**
     * 错误返回.
     *
     * @param errorCode
     *            子状态码，出现在error
     * @param message
     * @return
     */
    public static MetaRestResponse error(int errorCode, String message) {
        MetaRestResponse response = new MetaRestResponse();
        String code = String.valueOf(errorCode);
        Integer responseCode = ResponseCode.ERROR;
        if (code.startsWith(String.valueOf(ResponseCode.BAD_REQUEST))) {
            responseCode = ResponseCode.BAD_REQUEST;
        } else if (code.startsWith(String.valueOf(ResponseCode.FORBIDDEN))) {
            responseCode = ResponseCode.FORBIDDEN;
        } else if (code.startsWith(String.valueOf(ResponseCode.UNAUTHORIZED))) {
            responseCode = ResponseCode.UNAUTHORIZED;
        } else if (code.startsWith(String.valueOf(ResponseCode.SUCCESS))) {
            responseCode = ResponseCode.SUCCESS;
        }
        return response.meta(responseCode).errors(errorCode, message);
    }

    /* ===================== 以下是系统内部使用方法，请勿调用================== */

    /**
     * 前端访问的endpoint.
     *
     * @param link
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MetaRestResponse handleLink(String link) {
        Map map = (Map) this.get(META);
        map.put("link", link);
        return this;
    }

    /**
     * http method.
     *
     * @param method
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MetaRestResponse handleMethod(String method) {
        Map map = (Map) this.get(META);
        map.put("method", method);
        return this;
    }

    /**
     * 前端的状态.
     *
     * @param method
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MetaRestResponse handleStore(Object method) {
        Map map = (Map) this.get(META);
        map.put("store", method);
        return this;
    }

    /**
     * 请求参数.
     *
     * @param parameters
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MetaRestResponse handleParameters(Map<String, Object> parameters) {
        Map map = (Map) this.get(META);
        map.put("parameters", parameters);
        return this;
    }

}
