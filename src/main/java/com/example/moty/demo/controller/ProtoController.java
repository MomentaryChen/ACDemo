package com.example.moty.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moty.demo.annotation.PreHandle;
import com.example.moty.demo.annotation.RequireRole;
import com.example.moty.demo.model.dto.ApiResponse;
import com.example.moty.demo.model.protobuf.UserProto;
import com.example.moty.demo.service.proto.UserGrpcService;
import com.example.moty.demo.util.ApiResponseUtil;

/**
 * Proto Controller V1
 * Provides proto test endpoints
 */
@RestController
@RequestMapping("/proto")
public class ProtoController {

    @Autowired
    private UserGrpcService userGrpcService;

    /**
     * Regular user endpoint - login required only
     */
    @GetMapping("/hello")
    @PreHandle
    @RequireRole("ROLE_USER")
    public ApiResponse<String> userEndpoint() {
        UserProto.User user = UserProto.User.newBuilder()
                .setId(1)
                .setName("John Doe")
                .setEmail("john.doe@example.com")
                .build();
        System.out.println("Proto User: " + user);

        return ApiResponseUtil.success("Hello, " + user.getName() + "! You are logged in.");
    }
}
