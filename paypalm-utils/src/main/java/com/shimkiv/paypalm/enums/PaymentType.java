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

package com.shimkiv.paypalm.enums;

/**
 * Payment Types Enum
 *
 * @author Serhii Shymkiv
 */
public enum PaymentType {
    SALE("sale");

    private String type = null;

    PaymentType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
