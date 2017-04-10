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

package com.shimkiv.paypalm.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.shimkiv.paypalm.properties.PropertiesManager.getPropertyValue;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Common Utils
 *
 * @author Serhii Shymkiv
 */

@SuppressWarnings("unchecked")
public class CommonUtils {
    private static final Logger LOG =
            LoggerFactory.getLogger(CommonUtils.class);

    private static final BigInteger UNIQUE_REF_XOR =
            new BigInteger( "3ffffffffffff", 16);

    public static final Gson GSON =
            new GsonBuilder().
                    setPrettyPrinting().
                    serializeNulls().
                    create();

    public static final Currency CURRENCY =
            Currency.getInstance("USD");

    public static final int FIRST_ELEMENT = 0;

    public static final String COMMON_ERROR_MSG =
            "Impossible to perform requested operation !";
    public static final String COMMON_TXN_ERROR_MSG =
            "Transaction can't be rolled backed !";

    // Prevents instantiation
    private CommonUtils() {}

    /**
     * Gets {@link EntityManager}
     *
     * @return {@link EntityManager}
     */
    public static EntityManager getEntityManager() {
        try {
            return (EntityManager) new InitialContext().
                    lookup(getPropertyValue("entity.manager.jndi.name"));
        } catch(Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);

            return null;
        }
    }

    /**
     * Gets authorized user name
     *
     * @return Authorized user name
     */
    public static String getAuthorizedUserName() {
        String userName =
                (String) SecurityUtils.
                        getSubject().
                        getPrincipal();

        if(StringUtils.
                isNotBlank(userName)) {
            try {
                return userName.
                        toLowerCase().
                        trim();
            } catch (Exception e) {
                LOG.debug(COMMON_ERROR_MSG, e);
            }
        }

        return EMPTY;
    }

    /**
     * Checks whether the collection is empty
     *
     * @param collection Collection
     * @return Whether the collection is empty
     */
    public static <T> boolean collectionIsEmpty(T collection) {
        if(collection != null) {
            if(Map.class.isAssignableFrom(collection.getClass())) {
                return ((Map) collection).isEmpty();
            } else if(List.class.isAssignableFrom(collection.getClass())) {
                return ((List) collection).isEmpty();
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * Checks whether the collection is not empty
     *
     * @param collection Collection
     * @return Whether the collection is not empty
     */
    public static <T> boolean collectionIsNotEmpty(T collection) {
        return !collectionIsEmpty(collection);
    }

    /**
     * Generates unique reference by ID
     *
     * @param pKey ID
     * @return Unique reference of ID
     */
    public static String generateUniqueRef(int pKey) {
        Random uniqueRefRandom =
                new Random(pKey);

        byte[] rndBuf = new byte[2];
        uniqueRefRandom.
                nextBytes(rndBuf);

        rndBuf[0] = (byte) (rndBuf[0] & 0x3);
        rndBuf[0] = (byte) (rndBuf[0] | 0x4);

        BigInteger bi =
                new BigInteger(
                        Integer.toString(pKey));
        bi = bi.setBit(37);

        byte[] res = new byte[7];
        byte[] tmpArr = bi.toByteArray();

        res[0] = rndBuf[0];
        res[1] = tmpArr[4];
        res[2] = tmpArr[3];
        res[3] = tmpArr[2];
        res[4] = tmpArr[1];
        res[5] = tmpArr[0];
        res[6] = rndBuf[1];

        BigInteger bRes =
                new BigInteger(1, res);
        bRes = bRes.xor(UNIQUE_REF_XOR);

        return bRes.
                toString(36).
                toUpperCase();
    }

    /**
     * Generates ID by unique reference
     *
     * @param uniqueRef Unique reference
     * @return ID of unique reference
     */
    public static int uniqueRefToPkey(String uniqueRef) {
        if(uniqueRef == null ||
                uniqueRef.length() != 10) {
            return -1;
        }

        try {
            BigInteger bRes =
                    new BigInteger(
                            uniqueRef, 36);
            bRes = bRes.xor(UNIQUE_REF_XOR);

            byte[] res = new byte[5];
            byte[] tmpArr = bRes.toByteArray();

            res[0] = tmpArr[5];
            res[1] = tmpArr[4];
            res[2] = tmpArr[3];
            res[3] = tmpArr[2];
            res[4] = tmpArr[1];

            bRes = new BigInteger(1, res);
            bRes = bRes.clearBit(37);

            int id = bRes.intValue();

            if(id > 0) {
                return id;
            }
        } catch(Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }

        return -1;
    }

    /**
     * Gets {@link NumberFormat}
     *
     * @return {@link NumberFormat}
     */
    public static NumberFormat getNumberFormat() {
        NumberFormat numberFormat =
                NumberFormat.
                        getInstance();
        numberFormat.
                setMaximumFractionDigits(
                        CURRENCY.
                                getDefaultFractionDigits());
        numberFormat.
                setMinimumFractionDigits(
                        CURRENCY.
                                getDefaultFractionDigits());
        numberFormat.
                setGroupingUsed(
                        false);

        return numberFormat;
    }

    /**
     * Masks string with provided pattern
     *
     * @param toMask String to mask
     * @param lastShown Last shown symbols count
     * @param mask Mask
     * @return Masked string
     */
    public static String maskString(String toMask,
                                    int lastShown,
                                    String mask) {
        if(StringUtils.isBlank(toMask)) {
            return null;
        } else if(lastShown >= toMask.length() ||
                lastShown < 0) {
            return StringUtils.repeat(
                    mask,
                    toMask.length());
        }

        return StringUtils.overlay(
                toMask,
                StringUtils.repeat(
                        mask,
                        toMask.length() -
                                lastShown),
                0,
                toMask.length() -
                        lastShown);
    }
}
