package com.example.moty.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * API 響應數據傳輸對象
 * 提供統一的 API 響應格式
 * 
 * @param <T> 響應數據類型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    /**
     * 響應狀態碼
     */
    private int code;
    
    /**
     * 響應消息
     */
    private String message;
    
    /**
     * 響應數據
     */
    private T data;
}
