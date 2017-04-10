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

/**
 * PayPal API Identity Error Response VO
 *
 * @author Serhii Shymkiv
 */

public class IdentityErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String error;

    @SerializedName("error_description")
    private String errorDescription;

    public String getError() {
        return error;
    }

    public IdentityErrorResponse setError(String error) {
        this.error = error;

        return this;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public IdentityErrorResponse setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;

        return this;
    }
}
