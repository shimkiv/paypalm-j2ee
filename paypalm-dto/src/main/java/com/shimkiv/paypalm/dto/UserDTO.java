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
@Table(name = "user")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    @Size(max = 30)
    private String name;

    @OneToOne(
            fetch=FetchType.EAGER,
            cascade=CascadeType.ALL
    )
    @JoinColumn(name = "settings_id")
    private UserSettingsDTO userSettingsDTO;

    @OneToOne(
            fetch=FetchType.EAGER,
            cascade=CascadeType.ALL
    )
    @JoinColumn(name = "access_token_id")
    private UserAccessTokenDTO userAccessTokenDTO;

    public UserDTO() {
        //
    }

    public int getId() {
        return id;
    }

    public UserDTO setId(int id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return StringUtils.isNotBlank(name) ?
                name : EMPTY;
    }

    public UserDTO setName(String name) {
        this.name = name;

        return this;
    }

    public UserSettingsDTO getUserSettingsDTO() {
        return userSettingsDTO;
    }

    public UserDTO setUserSettingsDTO(UserSettingsDTO
                                              userSettingsDTO) {
        this.userSettingsDTO =
                userSettingsDTO;

        return this;
    }

    public UserAccessTokenDTO getUserAccessTokenDTO() {
        return userAccessTokenDTO;
    }

    public UserDTO setUserAccessTokenDTO(UserAccessTokenDTO
                                                 userAccessTokenDTO) {
        this.userAccessTokenDTO = userAccessTokenDTO;

        return this;
    }
}
