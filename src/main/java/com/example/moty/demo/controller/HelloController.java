package com.example.moty.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moty.demo.annotation.Audit;
import com.example.moty.demo.annotation.PreHandle;
import com.example.moty.demo.annotation.RequireRole;
import com.example.moty.demo.constant.ApiPathConstant;
import com.example.moty.demo.enums.AuditEnum;
import com.example.moty.demo.model.dto.ApiResponse;
import com.example.moty.demo.service.AsyncService;
import com.example.moty.demo.util.ApiResponseUtil;

/**
 * Basic Controller V1
 * Provides basic test endpoints
 */
@RestController
@RequestMapping(ApiPathConstant.API_V1)
public class HelloController {

    @Autowired
    private AsyncService asyncService;

    /**
     * Basic test endpoint - requires admin permission
     */
    @GetMapping("/hello")
    @PreHandle
    @RequireRole(value = "ROLE_ADMIN", message = "Admin permission required")
    @Audit(action = AuditEnum.HELLO)
    public ApiResponse<String> hello() {
        return ApiResponseUtil.success("Hello, World! You have admin permissions.");
    }

    /**
     * Regular user endpoint - login required only
     */
    @GetMapping("/user")
    @PreHandle
    @RequireRole("ROLE_USER")
    public ApiResponse<String> userEndpoint() {
        return ApiResponseUtil.success("Hello, User! You are logged in.");
    }

    /**
     * Multi-role endpoint - accessible by both admin and user
     */
    @GetMapping("/common")
    @PreHandle
    @RequireRole(value = { "ROLE_ADMIN", "ROLE_USER" }, message = "Logged-in user permission required")
    public ApiResponse<String> commonEndpoint() {
        return ApiResponseUtil.success("Hello! Welcome authenticated user.");
    }

    @GetMapping("/async")
    @PreHandle
    public ApiResponse<String> asyncEndpoint() {
        asyncService.asyncTask();
        return ApiResponseUtil.success("Async task started.");
    }

    @GetMapping("/async1")
    @PreHandle
    public ApiResponse<String> asyncEndpoint1() throws Exception {
        return ApiResponseUtil.success(asyncService.asyncTask1().get());
    }
}
