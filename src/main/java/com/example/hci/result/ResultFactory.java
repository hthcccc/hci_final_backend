package com.example.hci.result;

/**
 * @description 响应结果生成工厂类
 */
public class ResultFactory {
    public static com.example.hci.result.Result buildSuccessResult(Object data) {
        return buildResult(ResultCode.SUCCESS, "成功", data);
    }

    public static com.example.hci.result.Result buildFailResult(String message) {
        return buildResult(ResultCode.FAIL, message, null);
    }

    public static com.example.hci.result.Result buildResult(ResultCode resultCode, String message, Object data) {
        return buildResult(resultCode.code, message, data);
    }

    public static com.example.hci.result.Result buildResult(int resultCode, String message, Object data) {
        return new com.example.hci.result.Result(resultCode, message, data);
    }
}
