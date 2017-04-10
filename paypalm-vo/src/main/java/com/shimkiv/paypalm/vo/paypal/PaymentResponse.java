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
 * PayPal API Payment Response VO
 *
 * @author Serhii Shymkiv
 */

public class PaymentResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String state;
    private List<Link> links;
    private ErrorResponse errorResponse;

    public String getId() {
        return id;
    }

    public PaymentResponse setId(String id) {
        this.id = id;

        return this;
    }

    public String getState() {
        return state;
    }

    public PaymentResponse setState(String state) {
        this.state = state;

        return this;
    }

    public List<Link> getLinks() {
        return links;
    }

    public PaymentResponse setLinks(List<Link> links) {
        this.links = links;

        return this;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public PaymentResponse setErrorResponse(ErrorResponse
                                                    errorResponse) {
        this.errorResponse =
                errorResponse;

        return this;
    }
}
