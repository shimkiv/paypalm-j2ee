/*
 * All materials herein: Copyright (c) 2000-2017 Serhii Shymkiv. All Rights Reserved.
 *
 * These materials are owned by Serhii Shymkiv and are protected by copyright laws
 * and international copyright treaties, as well as other intellectual property laws
 * and treaties.
 *
 * All right, title and interest in the copyright, confidential information,
 * patents, design rights and all other intellectual property rights of
 * whatsoever nature in and to these materials are and shall remain the sole
 * and exclusive property of Serhii Shymkiv.
 */

package com.shimkiv.paypalm.crypto;

import org.apache.shiro.crypto.AesCipherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

import static com.shimkiv.paypalm.properties.PropertiesManager.getPropertyValue;
import static com.shimkiv.paypalm.utils.CommonUtils.COMMON_ERROR_MSG;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Common Crypter
 *
 * @author Serhii Shymkiv
 */

public class Crypter {
    private static final Logger LOG =
            LoggerFactory.getLogger(Crypter.class);

    // Prevents instantiation
    private Crypter() {}

    /**
     * Encrypts data
     *
     * @param toEncrypt Data to encrypt
     * @return Encrypted data
     */
    public static String encrypt(String toEncrypt) {
        try {
            AesCipherService aesCipher =
                    new AesCipherService();
            aesCipher.setModeName("ECB");

            return Base64.getEncoder().
                    encodeToString(
                            aesCipher.
                                    encrypt(
                                            toEncrypt.getBytes(
                                                    getPropertyValue("default.encoding")),
                                            getPropertyValue("crypter.key").
                                                    getBytes()).
                                    getBytes());
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }

        return EMPTY;
    }

    /**
     * Decrypts data
     *
     * @param toDecrypt Data to decrypt
     * @return Decrypted data
     */
    public static String decrypt(String toDecrypt) {
        try {
            AesCipherService aesCipher =
                    new AesCipherService();
            aesCipher.setModeName("ECB");

            return new String(
                    aesCipher.
                            decrypt(
                                    Base64.getDecoder().
                                            decode(toDecrypt),
                                    getPropertyValue("crypter.key").
                                            getBytes()).
                            getBytes(),
                    getPropertyValue("default.encoding"));
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }

        return EMPTY;
    }
}
