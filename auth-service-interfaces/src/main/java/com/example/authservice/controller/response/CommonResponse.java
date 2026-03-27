package com.example.authservice.controller.response;

public class CommonResponse<T> {
    private int code;          // 状态码
    private String message;    // 提示信息
    private T data;            // 数据内容

    public CommonResponse() {}

    public CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(200, "操作成功", data);
    }

    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse<>(200, message, data);
    }

    public static <T> CommonResponse<T> failure(int code, String message) {
        return new CommonResponse<>(code, message, null);
    }

    // getter/setter
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
