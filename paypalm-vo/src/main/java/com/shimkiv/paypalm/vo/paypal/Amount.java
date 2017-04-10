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
 * PayPal API Amount VO
 *
 * @author Serhii Shymkiv
 */

public class Amount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String total;
    private String currency;

    public String getTotal() {
        return total;
    }

    public Amount setTotal(String total) {
        this.total = total;

        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public Amount setCurrency(String currency) {
        this.currency = currency;

        return this;
    }
}
