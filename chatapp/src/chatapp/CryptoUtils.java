package chatapp;

// Utility class for AES encryption and decryption of chat messages
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtils {
    // 16-character secret key for AES
    private static final String KEY = "1234567890123456";

    /**
     * Encrypts a string using AES encryption and encodes it in Base64.
     * 
     * @param strToEncrypt The plain text message to encrypt.
     * @return The encrypted message as a Base64 string, or null if encryption
     *         fails.
     */
    public static String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            // In production, log the error
            return null;
        }
    }

    /**
     * Decrypts a Base64-encoded AES-encrypted string.
     * 
     * @param strToDecrypt The encrypted message as a Base64 string.
     * @return The decrypted plain text message, or null if decryption fails.
     */
    public static String decrypt(String strToDecrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            // In production, log the error
            return null;
        }
    }
}
