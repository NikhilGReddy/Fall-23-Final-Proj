package ClientSide;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptorDecryptor {

    private static final String SECRET_KEY = "HolyMolyHolyMoly";

    public static String encrypt(String plainText) {
        try {
            // Convert the plain text string to a byte array
            byte[] plainTextBytes = plainText.getBytes("UTF-8");

            // Create a secret key   object from the secret key string
            SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            // Create a Cipher instance for encryption
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Perform encryption and convert the result to a Base64 string
            byte[] encryptedBytes = cipher.doFinal(plainTextBytes);
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedText) {
        try {
            // Decode the Base64 encoded encrypted string
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

            // Create a secret key object from the secret key string
            SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

            // Create a Cipher instance for decryption
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            // Perform decryption and convert the result to a plain text string
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
