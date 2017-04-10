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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * JPA User Settings DTO
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "user_settings")
public class UserSettingsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "first_name")
    @Size(max = 128)
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 128)
    private String lastName;

    @Column(name = "email")
    @Size(max = 128)
    @Email
    private String email;

    @Column(name = "paypal_client_id")
    @Size(max = 128)
    private String payPalClientId;

    @Column(name = "paypal_secret")
    @Size(max = 128)
    private String payPalSecretPhrase;

    public UserSettingsDTO() {
        //
    }

    public int getId() {
        return id;
    }

    public UserSettingsDTO setId(int id) {
        this.id = id;

        return this;
    }

    public String getFirstName() {
        return StringUtils.isNotBlank(firstName) ?
                firstName : EMPTY;
    }

    public UserSettingsDTO setFirstName(String firstName) {
        this.firstName = firstName;

        return this;
    }

    public String getLastName() {
        return StringUtils.isNotBlank(lastName) ?
                lastName : EMPTY;
    }

    public UserSettingsDTO setLastName(String lastName) {
        this.lastName = lastName;

        return this;
    }

    public String getEmail() {
        return StringUtils.isNotBlank(email) ?
                email : EMPTY;
    }

    public UserSettingsDTO setEmail(String email) {
        this.email = email;

        return this;
    }

    public String getPayPalClientId() {
        return StringUtils.isNotBlank(payPalClientId) ?
                payPalClientId : EMPTY;
    }

    public UserSettingsDTO setPayPalClientId(String payPalClientId) {
        this.payPalClientId =
                payPalClientId;

        return this;
    }

    public String getPayPalSecretPhrase() {
        return StringUtils.isNotBlank(payPalSecretPhrase) ?
                payPalSecretPhrase : EMPTY;
    }

    public UserSettingsDTO setPayPalSecretPhrase(String payPalSecretPhrase) {
        this.payPalSecretPhrase =
                payPalSecretPhrase;

        return this;
    }
}
