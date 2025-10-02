package com.example.moty.demo.enums;

/**
 * HTTP 狀態碼枚舉
 * 定義標準的 HTTP 響應狀態碼和對應的消息
 */
public enum HttpStatusEnum {
    
    // 2xx 成功
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    
    // 3xx 重定向
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    NOT_MODIFIED(304, "Not Modified"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, "Permanent Redirect"),
    
    // 4xx 客戶端錯誤
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    CONFLICT(409, "Conflict"),
    GONE(410, "Gone"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    
    // 5xx 服務器錯誤
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");
    
    private final int code;
    private final String message;
    
    HttpStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    /**
     * 獲取狀態碼
     * 
     * @return HTTP 狀態碼
     */
    public int getCode() {
        return code;
    }
    
    /**
     * 獲取狀態消息
     * 
     * @return HTTP 狀態消息
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 根據狀態碼獲取枚舉值
     * 
     * @param code HTTP 狀態碼
     * @return 對應的枚舉值，如果不存在則返回 null
     */
    public static HttpStatusEnum fromCode(int code) {
        for (HttpStatusEnum status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
    
    /**
     * 檢查是否為成功狀態碼（2xx）
     * 
     * @return 是否為成功狀態碼
     */
    public boolean isSuccess() {
        return code >= 200 && code < 300;
    }
    
    /**
     * 檢查是否為客戶端錯誤狀態碼（4xx）
     * 
     * @return 是否為客戶端錯誤狀態碼
     */
    public boolean isClientError() {
        return code >= 400 && code < 500;
    }
    
    /**
     * 檢查是否為服務器錯誤狀態碼（5xx）
     * 
     * @return 是否為服務器錯誤狀態碼
     */
    public boolean isServerError() {
        return code >= 500 && code < 600;
    }
    
    /**
     * 檢查是否為重定向狀態碼（3xx）
     * 
     * @return 是否為重定向狀態碼
     */
    public boolean isRedirect() {
        return code >= 300 && code < 400;
    }
    
    /**
     * 檢查是否為錯誤狀態碼（4xx 或 5xx）
     * 
     * @return 是否為錯誤狀態碼
     */
    public boolean isError() {
        return isClientError() || isServerError();
    }
    
    @Override
    public String toString() {
        return code + " " + message;
    }
}
