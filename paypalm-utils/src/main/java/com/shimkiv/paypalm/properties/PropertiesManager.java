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

package com.shimkiv.paypalm.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Common Properties Manager
 *
 * @author Serhii Shymkiv
 */

public class PropertiesManager {
    private static final Logger LOG =
            LoggerFactory.getLogger(PropertiesManager.class);

    private static Properties properties;

    static {
        properties =
                new Properties();

        loadProperties(
                PropertiesManager.class.
                        getClassLoader().
                        getResourceAsStream("app.properties"));
    }

    // Prevent instantiation
    private PropertiesManager() {}

    /**
     * Gets property value
     *
     * @param propertyKey Property key
     * @return {@link String} of property value
     */
    public static String getPropertyValue(String propertyKey) {
        return properties.
                getProperty(
                        propertyKey);
    }

    private static void loadProperties(InputStream inputStream) {
        try {
            properties.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            LOG.error("Impossible to operate with Properties file !", e);

            throw new RuntimeException(e);
        }
    }
}
