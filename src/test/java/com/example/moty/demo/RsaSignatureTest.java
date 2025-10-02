package com.example.moty.demo;

import com.example.moty.demo.util.RsaSignatureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

/**
 * RSA 數字簽名測試類
 * 測試 RSA 密鑰生成、簽名和驗證功能
 */
@SpringBootTest
public class RsaSignatureTest {
    
    private RsaSignatureUtil rsaSignatureUtil = new RsaSignatureUtil();
    
    @Test
    public void testRsaSignature() throws Exception {
        // 測試密鑰對生成
        KeyPair keyPair = rsaSignatureUtil.generateKeyPair();
        assertNotNull(keyPair, "密鑰對不能為空");
        assertNotNull(keyPair.getPublic(), "公鑰不能為空");
        assertNotNull(keyPair.getPrivate(), "私鑰不能為空");
        
        System.out.println("公鑰: " + rsaSignatureUtil.publicKeyToString(keyPair.getPublic()));
        System.out.println("私鑰: " + rsaSignatureUtil.privateKeyToString(keyPair.getPrivate()));
        
        // 測試簽名和驗證
        String testData = "這是一個測試數據";
        String signature = rsaSignatureUtil.sign(testData, keyPair.getPrivate());
        assertNotNull(signature, "簽名不能為空");
        
        boolean isValid = rsaSignatureUtil.verify(testData, signature, keyPair.getPublic());
        assertTrue(isValid, "簽名驗證應該成功");
        
        System.out.println("測試數據: " + testData);
        System.out.println("簽名: " + signature);
        System.out.println("驗證結果: " + isValid);
    }
    
    @Test
    public void testRsaSignatureWithBytes() throws Exception {
        // 測試密鑰對生成
        KeyPair keyPair = rsaSignatureUtil.generateKeyPair();
        
        // 測試字節數組簽名和驗證
        byte[] testData = "這是一個字節數組測試數據".getBytes("UTF-8");
        String signature = rsaSignatureUtil.signBytes(testData, keyPair.getPrivate());
        assertNotNull(signature, "簽名不能為空");
        
        boolean isValid = rsaSignatureUtil.verifyBytes(testData, signature, keyPair.getPublic());
        assertTrue(isValid, "字節數組簽名驗證應該成功");
        
        System.out.println("字節數組測試數據長度: " + testData.length);
        System.out.println("字節數組簽名: " + signature);
        System.out.println("字節數組驗證結果: " + isValid);
    }
    
    @Test
    public void testRsaSignatureUtil() {
        // 測試工具類的測試方法
        String testData = "工具類測試數據";
        boolean result = rsaSignatureUtil.testSignature(testData);
        assertTrue(result, "工具類簽名測試應該成功");
        
        byte[] testBytes = "工具類字節數組測試數據".getBytes();
        boolean bytesResult = rsaSignatureUtil.testSignatureBytes(testBytes);
        assertTrue(bytesResult, "工具類字節數組簽名測試應該成功");
    }
}
