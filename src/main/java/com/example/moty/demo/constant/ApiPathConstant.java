package com.example.moty.demo.constant;

/**
 * API path constants
 * Defines all API version paths
 */
public class ApiPathConstant {
    
    // API version paths
    public static final String API_V1 = "/api/v1";
    public static final String API_V2 = "/api/v2";
    public static final String API_V3 = "/api/v3";
    
    // Currently used API version
    public static final String CURRENT_API_VERSION = API_V1;
    
    // Private constructor to prevent instantiation
    private ApiPathConstant() {
        throw new UnsupportedOperationException("Constant class cannot be instantiated");
    }
}
