package com.example.moty.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.moty.demo.annotation.PreHandle;
import com.example.moty.demo.constant.ApiPathConstant;
import com.example.moty.demo.model.dto.ApiResponse;
import com.example.moty.demo.model.dto.SignatureDto;
import com.example.moty.demo.model.vo.SignatureRequest;
import com.example.moty.demo.service.SignatureService;
import com.example.moty.demo.util.ApiResponseUtil;

/**
 * RSA 數字簽名控制器 V1
 * 提供 RSA 密鑰生成、數字簽名和驗證功能
 */
@RestController
@RequestMapping(ApiPathConstant.API_V1 + "/rsa")
public class RsaController {
    
    @Autowired
    private SignatureService signatureService;
    
    /**
     * 生成 RSA 密鑰對
     */
    @PostMapping("/generate-keys")
    @PreHandle
    public ApiResponse<SignatureDto> generateKeyPair() {
        return ApiResponseUtil.success(signatureService.generateKeyPair());
    }
    
    /**
     * 對數據進行數字簽名
     */
    @PostMapping("/sign")
    @PreHandle
    public ApiResponse<SignatureDto> signData(@RequestBody SignatureRequest request) {
        return ApiResponseUtil.success(signatureService.signData(request));
    }
    
    /**
     * 驗證數字簽名
     */
    @PostMapping("/verify")
    @PreHandle
    public ApiResponse<SignatureDto> verifySignature(@RequestBody SignatureRequest request) {
        return ApiResponseUtil.success(signatureService.verifySignature(request));
    }
    
    /**
     * 完整的簽名和驗證流程演示
     */
    @PostMapping("/complete-flow")
    @PreHandle
    public ApiResponse<SignatureDto> completeSignatureFlow(@RequestParam String data) {
        return ApiResponseUtil.success(signatureService.completeSignatureFlow(data));
    }
    
    /**
     * 測試簽名功能
     */
    @GetMapping("/test")
    @PreHandle
    public ApiResponse<SignatureDto> testSignature() {
        String testData = "這是一個測試數據 - " + System.currentTimeMillis();
        return ApiResponseUtil.success(signatureService.completeSignatureFlow(testData));
    }
    
    /**
     * 使用 GET 參數進行簽名
     */
    @GetMapping("/sign-get")
    @PreHandle
    public ApiResponse<SignatureDto> signDataGet(@RequestParam String data, @RequestParam String privateKey) {
        SignatureRequest request = SignatureRequest.builder()
                .data(data)
                .privateKey(privateKey)
                .build();
        return ApiResponseUtil.success(signatureService.signData(request));
    }
    
    /**
     * 使用 GET 參數驗證簽名
     */
    @GetMapping("/verify-get")
    @PreHandle
    public ApiResponse<SignatureDto> verifySignatureGet(@RequestParam String data, 
        @RequestParam String signature, @RequestParam String publicKey) {
        SignatureRequest request = SignatureRequest.builder()
                .data(data)
                .signature(signature)
                .publicKey(publicKey)
                .build();
        return ApiResponseUtil.success(signatureService.verifySignature(request));
    }
}
