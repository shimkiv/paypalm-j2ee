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
 * PayPal API Access Token Response VO
 *
 * @author Serhii Shymkiv
 */

public class AccessTokenResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public AccessTokenResponse setAccessToken(String accessToken) {
        this.accessToken = accessToken;

        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public AccessTokenResponse setTokenType(String tokenType) {
        this.tokenType = tokenType;

        return this;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public AccessTokenResponse setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;

        return this;
    }
}
