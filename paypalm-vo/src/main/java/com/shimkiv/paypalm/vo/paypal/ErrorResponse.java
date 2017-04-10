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
 * PayPal API Error Response VO
 *
 * @author Serhii Shymkiv
 */

public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String message;

    @SerializedName("information_link")
    private String informationLink;

    @SerializedName("details")
    private List<ErrorDetail> errorDetails;

    public String getName() {
        return name;
    }

    public ErrorResponse setName(String name) {
        this.name = name;

        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;

        return this;
    }

    public String getInformationLink() {
        return informationLink;
    }

    public ErrorResponse setInformationLink(String informationLink) {
        this.informationLink = informationLink;

        return this;
    }

    public List<ErrorDetail> getErrorDetails() {
        return errorDetails;
    }

    public ErrorResponse setErrorDetails(List<ErrorDetail>
                                                 errorDetails) {
        this.errorDetails = errorDetails;

        return this;
    }
}
