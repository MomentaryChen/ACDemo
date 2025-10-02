package com.example.moty.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 數字簽名請求 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignatureRequest {
    
    private String data;
    private String publicKey;
    private String privateKey;
    private String signature;
}
