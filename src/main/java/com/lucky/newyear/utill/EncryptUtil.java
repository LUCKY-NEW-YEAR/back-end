package com.lucky.newyear.utill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class EncryptUtil {

    private static final String ALGORITHM = "AES";

    // 암호화
    public static String encrypt(String data, String key) { // properties에서 주입받은 키 사용
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData); // Base64로 인코딩해서 저장
        } catch (Exception e) {
            log.error("postRecipeTest() 암호화 오류", e.getMessage());
            throw new NyException(HttpStatus.INTERNAL_SERVER_ERROR, "서버측 오류 발생");
        }
    }

    // 복호화
    public static String decrypt(String encryptedData, String key){
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData); // 복호화된 데이터 반환
        } catch (Exception e) {
            log.error("postRecipeTest() 복호화 오류", e.getMessage());
            throw new NyException(HttpStatus.INTERNAL_SERVER_ERROR, "서버측 오류 발생");
        }
    }
}
