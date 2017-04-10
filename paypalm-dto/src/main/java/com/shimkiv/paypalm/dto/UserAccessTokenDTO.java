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

package com.shimkiv.paypalm.dto;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Calendar;

/**
 * JPA User Access Token DTO
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "access_token")
public class UserAccessTokenDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDate;

    @Column(name = "expires_in")
    private int expiresIn;

    @Column(name = "token_type")
    @Size(max = 128)
    private String tokenType;

    @Column(name = "access_token")
    @Size(max = 256)
    private String accessToken;

    public UserAccessTokenDTO() {
        //
    }

    public int getId() {
        return id;
    }

    public UserAccessTokenDTO setId(int id) {
        this.id = id;

        return this;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public UserAccessTokenDTO setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;

        return this;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public UserAccessTokenDTO setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;

        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public UserAccessTokenDTO setTokenType(String tokenType) {
        this.tokenType = tokenType;

        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public UserAccessTokenDTO setAccessToken(String accessToken) {
        this.accessToken = accessToken;

        return this;
    }
}
