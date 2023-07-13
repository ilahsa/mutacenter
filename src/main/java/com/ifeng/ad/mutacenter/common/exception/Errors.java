package com.ifeng.ad.mutacenter.common.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目描述: 基础错误类型
 */
public class Errors {

    protected static Map<Integer, Integer> HTTP_CODE;

    public static int NO_ERROR = 0; // 无异常
    public static int INTERNAL_ERROR = 1; // 系统异常
    public static int PARAM_ERROR = 2; // 参数异常
    public static int API_UNSUPPORTED = 3; // API不支持
    public static int ABOLITION_API = 4; // 取消的API
    public static int VERIFICATION_FAILURE = 5; // 校验失败
    public static int NO_CONTENT = 6; // 无内容

    static {
        HTTP_CODE = new HashMap<Integer, Integer>();
        HTTP_CODE.put(INTERNAL_ERROR, 500);
        HTTP_CODE.put(API_UNSUPPORTED, 403);
        HTTP_CODE.put(PARAM_ERROR, 403);
        HTTP_CODE.put(ABOLITION_API, 403);
        HTTP_CODE.put(VERIFICATION_FAILURE, 403);
        HTTP_CODE.put(NO_CONTENT, 204);
        HTTP_CODE.put(NO_ERROR, 200);
    }

    /**
     * 根据错误类型的{@code code}获取对应的{@code HTTP_CODE}
     * @param code 错误类型的{@code code}
     * @return 返回对应的{@code HTTP_CODE}
     */
    public static int getHttpCode(int code) {
        return HTTP_CODE.get(code);
    }
}
