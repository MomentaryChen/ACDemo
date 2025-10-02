package com.example.moty.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 數字簽名響應 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignatureDto {
    
    private boolean success;    
    private String message;
    private String publicKey;
    private String privateKey;
    private String signature;
    private boolean verified;
}
