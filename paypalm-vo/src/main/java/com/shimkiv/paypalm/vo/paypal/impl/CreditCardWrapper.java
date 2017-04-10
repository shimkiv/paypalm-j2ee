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

package com.shimkiv.paypalm.vo.paypal.impl;

import com.google.gson.annotations.SerializedName;
import com.shimkiv.paypalm.vo.paypal.FundingInstrument;

import java.io.Serializable;

/**
 * PayPal API Credit Card Funding Instruments VO Wrapper
 * (to not spend much time on custom JsonSerializer(s))
 *
 * @author Serhii Shymkiv
 */

public class CreditCardWrapper implements FundingInstrument, Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("credit_card")
    private CreditCard creditCard;

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public CreditCardWrapper setCreditCard(CreditCard
                                                   creditCard) {
        this.creditCard = creditCard;

        return this;
    }
}
