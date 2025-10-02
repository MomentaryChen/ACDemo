package com.example.moty.demo.util;

import com.example.moty.demo.enums.HttpStatusEnum;
import com.example.moty.demo.model.dto.ApiResponse;

/**
 * API response utility class
 * Provides standardized API response creation methods
 */
public class ApiResponseUtil<T>{
    
    /**
     * 創建成功響應
     * 
     * @param data 響應數據
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(HttpStatusEnum.OK.getCode())
                .message(HttpStatusEnum.OK.getMessage())
                .data(data)
                .build();
    }
    
    /**
     * 創建成功響應（自定義消息）
     * 
     * @param message 自定義消息
     * @param data 響應數據
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .code(HttpStatusEnum.OK.getCode())
                .message(message)
                .data(data)
                .build();
    }
    
    /**
     * 創建錯誤響應
     * 
     * @param status HTTP 狀態枚舉
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> error(HttpStatusEnum status, String message) {
        return ApiResponse.<T>builder()
                .code(status.getCode())
                .message(message)
                .data(null)
                .build();
    }
    
    /**
     * 創建錯誤響應（使用狀態枚舉的默認消息）
     * 
     * @param status HTTP 狀態枚舉
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> error(HttpStatusEnum status) {
        return ApiResponse.<T>builder()
                .code(status.getCode())
                .message(status.getMessage())
                .data(null)
                .build();
    }
    
    /**
     * 創建 400 Bad Request 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> badRequest(String message) {
        return error(HttpStatusEnum.BAD_REQUEST, message);
    }
    
    /**
     * 創建 401 Unauthorized 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> unauthorized(String message) {
        return error(HttpStatusEnum.UNAUTHORIZED, message);
    }
    
    /**
     * 創建 403 Forbidden 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> forbidden(String message) {
        return error(HttpStatusEnum.FORBIDDEN, message);
    }
    
    /**
     * 創建 404 Not Found 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> notFound(String message) {
        return error(HttpStatusEnum.NOT_FOUND, message);
    }
    
    /**
     * 創建 500 Internal Server Error 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> internalError(String message) {
        return error(HttpStatusEnum.INTERNAL_SERVER_ERROR, message);
    }
    
    /**
     * 創建 503 Service Unavailable 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> serviceUnavailable(String message) {
        return error(HttpStatusEnum.SERVICE_UNAVAILABLE, message);
    }
    
    /**
     * 創建 201 Created 響應
     * 
     * @param data 創建的資源數據
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
                .code(HttpStatusEnum.CREATED.getCode())
                .message(HttpStatusEnum.CREATED.getMessage())
                .data(data)
                .build();
    }
    
    /**
     * 創建 204 No Content 響應
     * 
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> noContent() {
        return ApiResponse.<T>builder()
                .code(HttpStatusEnum.NO_CONTENT.getCode())
                .message(HttpStatusEnum.NO_CONTENT.getMessage())
                .data(null)
                .build();
    }
    
    /**
     * 創建 409 Conflict 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> conflict(String message) {
        return error(HttpStatusEnum.CONFLICT, message);
    }
    
    /**
     * 創建 408 Request Timeout 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> requestTimeout(String message) {
        return error(HttpStatusEnum.REQUEST_TIMEOUT, message);
    }
    
    /**
     * 創建 429 Too Many Requests 響應
     * 
     * @param message 錯誤消息
     * @return API 響應對象
     */
    public static <T> ApiResponse<T> tooManyRequests(String message) {
        return error(HttpStatusEnum.TOO_MANY_REQUESTS, message);
    }
}
