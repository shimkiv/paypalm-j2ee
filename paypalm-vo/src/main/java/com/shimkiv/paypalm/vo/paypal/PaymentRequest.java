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
import java.util.List;

/**
 * PayPal API Payment Request VO
 *
 * @author Serhii Shymkiv
 */

public class PaymentRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String intent;
    private Payer payer;
    private List<Transaction> transactions;

    public String getIntent() {
        return intent;
    }

    public PaymentRequest setIntent(String intent) {
        this.intent = intent;

        return this;
    }

    public Payer getPayer() {
        return payer;
    }

    public PaymentRequest setPayer(Payer payer) {
        this.payer = payer;

        return this;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public PaymentRequest setTransactions(List<Transaction>
                                                  transactions) {
        this.transactions = transactions;

        return this;
    }
}
