package org.example.plant.realization;

import org.example.plant.protocol.Enigma;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

public class PasswordManager implements Enigma {
    private static Enigma instance;

    public static Enigma getInstance() {
        if (instance == null) {
            instance = new PasswordManager();
        }
        return instance;
    }

    public static void setInstance(Enigma testInstance) { instance = testInstance; }

    @Override
    public SecretKey generateKey(String key1, String key2) throws Exception {
        // Combine the keys and create a hash
        String combinedKey = key1 + key2;
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(combinedKey.getBytes("UTF-8"));
        return new SecretKeySpec(key, "AES");
    }

    @Override
    public String encryptPassword(String password, String key1, String key2) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey secretKey = generateKey(key1, key2);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        String encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedPassword;
    }

    @Override
    public String decryptPassword(String password, String key1, String key2) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, generateKey(key1, key2));
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(password));
        return new String(decryptedBytes);
    }
}
