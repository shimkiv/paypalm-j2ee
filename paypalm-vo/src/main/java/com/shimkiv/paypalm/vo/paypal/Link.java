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
 * PayPal API Link VO
 *
 * @author Serhii Shymkiv
 */

public class Link implements Serializable {
    private static final long serialVersionUID = 1L;

    private String href;
    private String rel;
    private String method;

    public String getHref() {
        return href;
    }

    public Link setHref(String href) {
        this.href = href;

        return this;
    }

    public String getRel() {
        return rel;
    }

    public Link setRel(String rel) {
        this.rel = rel;

        return this;
    }

    public String getMethod() {
        return method;
    }

    public Link setMethod(String method) {
        this.method = method;

        return this;
    }
}
