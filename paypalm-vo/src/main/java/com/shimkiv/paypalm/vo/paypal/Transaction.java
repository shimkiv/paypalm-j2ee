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

package com.shimkiv.paypalm.vo.paypal;

import java.io.Serializable;

/**
 * PayPal API Transaction VO
 *
 * @author Serhii Shymkiv
 */

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private Amount amount;
    private String description;

    public Amount getAmount() {
        return amount;
    }

    public Transaction setAmount(Amount amount) {
        this.amount = amount;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public Transaction setDescription(String description) {
        this.description = description;

        return this;
    }
}
