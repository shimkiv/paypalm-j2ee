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

package com.shimkiv.paypalm.dao;

import com.shimkiv.paypalm.dto.UserDTO;

import java.util.Calendar;

/**
 * User DAO Interface
 *
 * @author Serhii Shymkiv
 */

public interface UserDAO {
    UserDTO getUser(
            String userName);
    void updateUserSettings(
            String userName,
            String firstName,
            String lastName,
            String email,
            String payPalClientId,
            String payPalSecretPhrase);
    void updateUserAccessToken(String userName,
                               Calendar creationDate,
                               int expiresIn,
                               String tokenType,
                               String accessToken);
}
