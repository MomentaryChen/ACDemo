package com.example.moty.demo.service;

import com.example.moty.demo.model.dto.SignatureDto;
import com.example.moty.demo.model.vo.SignatureRequest;
import com.example.moty.demo.util.RsaSignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 數字簽名服務類
 * 提供數字簽名的業務邏輯處理
 */
@Service
public class SignatureService {
    
    private static final Logger logger = LoggerFactory.getLogger(SignatureService.class);
    
    @Autowired
    private RsaSignatureUtil rsaSignatureUtil;
    
    /**
     * 生成新的 RSA 密鑰對
     * 
     * @return 包含公鑰和私鑰的響應
     */
    public SignatureDto generateKeyPair() {
        try {
            KeyPair keyPair = rsaSignatureUtil.generateKeyPair();
            String publicKeyString = rsaSignatureUtil.publicKeyToString(keyPair.getPublic());
            String privateKeyString = rsaSignatureUtil.privateKeyToString(keyPair.getPrivate());
            
            logger.info("成功生成新的 RSA 密鑰對");
            return SignatureDto.builder()
                    .success(true)
                    .message("密鑰對生成成功")
                    .publicKey(publicKeyString)
                    .privateKey(privateKeyString)
                    .build();
        } catch (Exception e) {
            logger.error("生成密鑰對失敗: {}", e.getMessage(), e);
            return SignatureDto.builder()
                    .success(false)
                    .message("生成密鑰對失敗: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * 對數據進行數字簽名
     * 
     * @param request 簽名請求
     * @return 簽名結果
     */
    public SignatureDto signData(SignatureRequest request) {
        try {
            if (request.getData() == null || request.getData().trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("數據不能為空")
                        .build();
            }
            
            if (request.getPrivateKey() == null || request.getPrivateKey().trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("私鑰不能為空")
                        .build();
            }
            
            // 從字符串恢復私鑰
            PrivateKey privateKey = rsaSignatureUtil.stringToPrivateKey(request.getPrivateKey());
            
            // 對數據進行簽名
            String signature = rsaSignatureUtil.sign(request.getData(), privateKey);
            
            logger.info("數據簽名成功，數據長度: {} 字符", request.getData().length());
            return SignatureDto.builder()
                    .success(true)
                    .message("數據簽名成功")
                    .signature(signature)
                    .build();
        } catch (Exception e) {
            logger.error("數據簽名失敗: {}", e.getMessage(), e);
            return SignatureDto.builder()
                    .success(false)
                    .message("數據簽名失敗: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * 驗證數字簽名
     * 
     * @param request 驗證請求
     * @return 驗證結果
     */
    public SignatureDto verifySignature(SignatureRequest request) {
        try {
            if (request.getData() == null || request.getData().trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("數據不能為空")
                        .build();
            }
            
            if (request.getSignature() == null || request.getSignature().trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("簽名不能為空")
                        .build();
            }
            
            if (request.getPublicKey() == null || request.getPublicKey().trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("公鑰不能為空")
                        .build();
            }
            
            // 從字符串恢復公鑰
            PublicKey publicKey = rsaSignatureUtil.stringToPublicKey(request.getPublicKey());
            
            // 驗證簽名
            boolean isValid = rsaSignatureUtil.verify(request.getData(), request.getSignature(), publicKey);
            
            String message = isValid ? "簽名驗證成功" : "簽名驗證失敗";
            logger.info("簽名驗證完成: {}", message);
            
            return SignatureDto.builder()
                    .success(true)
                    .message(message)
                    .signature(request.getSignature())
                    .verified(isValid)
                    .build();
        } catch (Exception e) {
            logger.error("簽名驗證失敗: {}", e.getMessage(), e);
            return SignatureDto.builder()
                    .success(false)
                    .message("簽名驗證失敗: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * 完整的簽名和驗證流程演示
     * 
     * @param data 要簽名的數據
     * @return 完整的簽名和驗證結果
     */
    public SignatureDto completeSignatureFlow(String data) {
        try {
            if (data == null || data.trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("數據不能為空")
                        .build();
            }
            
            // 1. 生成密鑰對
            KeyPair keyPair = rsaSignatureUtil.generateKeyPair();
            String publicKeyString = rsaSignatureUtil.publicKeyToString(keyPair.getPublic());
            String privateKeyString = rsaSignatureUtil.privateKeyToString(keyPair.getPrivate());
            
            // 2. 對數據進行簽名
            String signature = rsaSignatureUtil.sign(data, keyPair.getPrivate());
            
            // 3. 驗證簽名
            boolean isValid = rsaSignatureUtil.verify(data, signature, keyPair.getPublic());
            
            logger.info("完整簽名流程執行成功，驗證結果: {}", isValid);
            
            return SignatureDto.builder()
                    .success(true)
                    .message("完整簽名流程執行成功")
                    .publicKey(publicKeyString)
                    .privateKey(privateKeyString)
                    .signature(signature)
                    .verified(isValid)
                    .build();
        } catch (Exception e) {
            logger.error("完整簽名流程執行失敗: {}", e.getMessage(), e);
            return SignatureDto.builder()
                    .success(false)
                    .message("完整簽名流程執行失敗: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * 對字節數組進行簽名
     * 
     * @param data 字節數組數據
     * @param privateKeyString 私鑰字符串
     * @return 簽名結果
     */
    public SignatureDto signBytes(byte[] data, String privateKeyString) {
        try {
            if (data == null || data.length == 0) {
                return SignatureDto.builder()
                        .success(false)
                        .message("數據不能為空")
                        .build();
            }
            
            if (privateKeyString == null || privateKeyString.trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("私鑰不能為空")
                        .build();
            }
            
            PrivateKey privateKey = rsaSignatureUtil.stringToPrivateKey(privateKeyString);
            String signature = rsaSignatureUtil.signBytes(data, privateKey);
            
            logger.info("字節數組簽名成功，數據長度: {} 字節", data.length);
            return SignatureDto.builder()
                    .success(true)
                    .message("字節數組簽名成功")
                    .signature(signature)
                    .build();
        } catch (Exception e) {
            logger.error("字節數組簽名失敗: {}", e.getMessage(), e);
            return SignatureDto.builder()
                    .success(false)
                    .message("字節數組簽名失敗: " + e.getMessage())
                    .build();
        }
    }
    
    /**
     * 驗證字節數組的簽名
     * 
     * @param data 字節數組數據
     * @param signature 簽名
     * @param publicKeyString 公鑰字符串
     * @return 驗證結果
     */
    public SignatureDto verifyBytes(byte[] data, String signature, String publicKeyString) {
        try {
            if (data == null || data.length == 0) {
                return SignatureDto.builder()
                        .success(false)
                        .message("數據不能為空")
                        .build();
            }
            
            if (signature == null || signature.trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("簽名不能為空")
                        .build();
            }
            
            if (publicKeyString == null || publicKeyString.trim().isEmpty()) {
                return SignatureDto.builder()
                        .success(false)
                        .message("公鑰不能為空")
                        .build();
            }
            
            PublicKey publicKey = rsaSignatureUtil.stringToPublicKey(publicKeyString);
            boolean isValid = rsaSignatureUtil.verifyBytes(data, signature, publicKey);
            
            String message = isValid ? "字節數組簽名驗證成功" : "字節數組簽名驗證失敗";
            logger.info("字節數組簽名驗證完成: {}", message);
            
            return SignatureDto.builder()
                    .success(true)
                    .message(message)
                    .signature(signature)
                    .verified(isValid)
                    .build();
        } catch (Exception e) {
            logger.error("字節數組簽名驗證失敗: {}", e.getMessage(), e);
            return SignatureDto.builder()
                    .success(false)
                    .message("字節數組簽名驗證失敗: " + e.getMessage())
                    .build();
        }
    }
}
