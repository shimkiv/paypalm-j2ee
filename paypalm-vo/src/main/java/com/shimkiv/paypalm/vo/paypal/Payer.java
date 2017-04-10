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

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * PayPal API Payment Request VO
 *
 * @author Serhii Shymkiv
 */

public class Payer implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("funding_instruments")
    private List<FundingInstrument> fundingInstruments;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Payer setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;

        return this;
    }

    public List<FundingInstrument> getFundingInstruments() {
        return fundingInstruments;
    }

    public Payer setFundingInstruments(List<FundingInstrument>
                                               fundingInstruments) {
        this.fundingInstruments =
                fundingInstruments;

        return this;
    }
}
