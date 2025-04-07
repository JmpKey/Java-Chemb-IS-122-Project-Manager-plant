package org.example.plant.protocol;

import javax.crypto.SecretKey;

public interface Enigma {
    SecretKey generateKey(String key1, String key2) throws Exception;

    String encryptPassword(String password, String key1, String key2) throws Exception;

    String decryptPassword(String password, String k1, String k2) throws Exception;
}
