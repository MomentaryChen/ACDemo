package com.example.moty.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA 數字簽名工具類
 * 提供 RSA 密鑰生成、數字簽名和驗證功能
 */
@Component
public class RsaSignatureUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(RsaSignatureUtil.class);
    
    // RSA 算法名稱
    private static final String RSA_ALGORITHM = "RSA";
    
    // 簽名算法名稱
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    
    // 密鑰大小
    private static final int KEY_SIZE = 2048;
    
    /**
     * 生成 RSA 密鑰對
     * 
     * @return RSA 密鑰對
     * @throws Exception 生成失敗時拋出異常
     */
    public KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }
    
    /**
     * 將公鑰轉換為 Base64 編碼的字符串
     * 
     * @param publicKey 公鑰
     * @return Base64 編碼的公鑰字符串
     */
    public String publicKeyToString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
    
    /**
     * 將私鑰轉換為 Base64 編碼的字符串
     * 
     * @param privateKey 私鑰
     * @return Base64 編碼的私鑰字符串
     */
    public String privateKeyToString(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }
    
    /**
     * 從 Base64 編碼的字符串恢復公鑰
     * 
     * @param publicKeyString Base64 編碼的公鑰字符串
     * @return 公鑰對象
     * @throws Exception 恢復失敗時拋出異常
     */
    public PublicKey stringToPublicKey(String publicKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePublic(keySpec);
    }
    
    /**
     * 從 Base64 編碼的字符串恢復私鑰
     * 
     * @param privateKeyString Base64 編碼的私鑰字符串
     * @return 私鑰對象
     * @throws Exception 恢復失敗時拋出異常
     */
    public PrivateKey stringToPrivateKey(String privateKeyString) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }
    
    /**
     * 對字符串數據進行數字簽名
     * 
     * @param data 要簽名的數據
     * @param privateKey 私鑰
     * @return Base64 編碼的簽名字符串
     * @throws Exception 簽名失敗時拋出異常
     */
    public String sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(signature.sign());
    }
    
    /**
     * 驗證字符串數據的數字簽名
     * 
     * @param data 原始數據
     * @param signatureString Base64 編碼的簽名字符串
     * @param publicKey 公鑰
     * @return 驗證結果
     * @throws Exception 驗證失敗時拋出異常
     */
    public boolean verify(String data, String signatureString, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data.getBytes("UTF-8"));
        byte[] signatureBytes = Base64.getDecoder().decode(signatureString);
        return signature.verify(signatureBytes);
    }
    
    /**
     * 對字節數組進行數字簽名
     * 
     * @param data 要簽名的字節數組
     * @param privateKey 私鑰
     * @return Base64 編碼的簽名字符串
     * @throws Exception 簽名失敗時拋出異常
     */
    public String signBytes(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);
        return Base64.getEncoder().encodeToString(signature.sign());
    }
    
    /**
     * 驗證字節數組的數字簽名
     * 
     * @param data 原始字節數組
     * @param signatureString Base64 編碼的簽名字符串
     * @param publicKey 公鑰
     * @return 驗證結果
     * @throws Exception 驗證失敗時拋出異常
     */
    public boolean verifyBytes(byte[] data, String signatureString, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        byte[] signatureBytes = Base64.getDecoder().decode(signatureString);
        return signature.verify(signatureBytes);
    }
    
    /**
     * 測試 RSA 簽名功能
     * 
     * @param testData 測試數據
     * @return 測試結果
     */
    public boolean testSignature(String testData) {
        try {
            // 生成密鑰對
            KeyPair keyPair = generateKeyPair();
            
            // 對數據進行簽名
            String signature = sign(testData, keyPair.getPrivate());
            
            // 驗證簽名
            boolean isValid = verify(testData, signature, keyPair.getPublic());
            
            logger.info("RSA 簽名測試完成，測試數據: {}, 驗證結果: {}", testData, isValid);
            
            return isValid;
        } catch (Exception e) {
            logger.error("RSA 簽名測試失敗: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 測試字節數組簽名功能
     * 
     * @param testData 測試字節數組
     * @return 測試結果
     */
    public boolean testSignatureBytes(byte[] testData) {
        try {
            // 生成密鑰對
            KeyPair keyPair = generateKeyPair();
            
            // 對字節數組進行簽名
            String signature = signBytes(testData, keyPair.getPrivate());
            
            // 驗證簽名
            boolean isValid = verifyBytes(testData, signature, keyPair.getPublic());
            
            logger.info("RSA 字節數組簽名測試完成，數據長度: {}, 驗證結果: {}", testData.length, isValid);
            
            return isValid;
        } catch (Exception e) {
            logger.error("RSA 字節數組簽名測試失敗: {}", e.getMessage(), e);
            return false;
        }
    }
}
