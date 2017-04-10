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
 * PayPal API Credit Card Funding Instruments VO
 *
 * @author Serhii Shymkiv
 */

public class CreditCard implements FundingInstrument, Serializable {
    private static final long serialVersionUID = 1L;

    private String number;
    private String type;

    @SerializedName("expire_month")
    private String expireMonth;

    @SerializedName("expire_year")
    private String expireYear;
    private String cvv2;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    public String getNumber() {
        return number;
    }

    public CreditCard setNumber(String number) {
        this.number = number;

        return this;
    }

    public String getType() {
        return type;
    }

    public CreditCard setType(String type) {
        this.type = type;

        return this;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public CreditCard setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;

        return this;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public CreditCard setExpireYear(String expireYear) {
        this.expireYear = expireYear;

        return this;
    }

    public String getCvv2() {
        return cvv2;
    }

    public CreditCard setCvv2(String cvv2) {
        this.cvv2 = cvv2;

        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CreditCard setFirstName(String firstName) {
        this.firstName = firstName;

        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CreditCard setLastName(String lastName) {
        this.lastName = lastName;

        return this;
    }
}
