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

package com.shimkiv.paypalm.dao.jpa;

import com.shimkiv.paypalm.dao.UserDAO;
import com.shimkiv.paypalm.dto.UserAccessTokenDTO;
import com.shimkiv.paypalm.dto.UserDTO;
import com.shimkiv.paypalm.dto.UserSettingsDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.Calendar;

/**
 * User DAO JPA Implementation
 *
 * @author Serhii Shymkiv
 */

public class UserDAOImpl extends BaseJPADAO implements UserDAO {
    public UserDAOImpl() {
        super();
    }

    public UserDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public UserDTO getUser(String userName)
            throws PersistenceException {
        try {
            return entityManager.
                    createQuery(
                            "SELECT u FROM UserDTO u WHERE u.name = :userName",
                            UserDTO.class).
                    setParameter("userName", userName).
                    getSingleResult();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateUserSettings(String userName,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String payPalClientId,
                                   String payPalSecretPhrase)
            throws PersistenceException {
        try {
            UserDTO userDTO =
                    getUser(userName);
            UserSettingsDTO userSettingsDTO =
                    userDTO.getUserSettingsDTO() == null ?
                            new UserSettingsDTO() :
                            userDTO.
                                    getUserSettingsDTO();
            UserAccessTokenDTO userAccessTokenDTO =
                    userDTO.getUserAccessTokenDTO() == null ?
                            new UserAccessTokenDTO() :
                            userDTO.
                                    getUserAccessTokenDTO();

            if((payPalClientId != null &&
                    !payPalClientId.
                            equalsIgnoreCase(
                                    userSettingsDTO.
                                            getPayPalClientId())) ||
                    payPalSecretPhrase != null &&
                            !payPalSecretPhrase.
                                    equalsIgnoreCase(
                                            userSettingsDTO.
                                                    getPayPalSecretPhrase())) {
                userDTO.
                        setUserAccessTokenDTO(
                                userAccessTokenDTO.
                                        setCreationDate(
                                                Calendar.getInstance()).
                                        setExpiresIn(0).
                                        setTokenType(null).
                                        setAccessToken(null));
            }

            userSettingsDTO.
                    setFirstName(firstName).
                    setLastName(lastName).
                    setEmail(email).
                    setPayPalClientId(
                            payPalClientId).
                    setPayPalSecretPhrase(
                            payPalSecretPhrase);

            userDTO.
                    setUserSettingsDTO(
                            userSettingsDTO);

            entityManager.
                    merge(userDTO);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateUserAccessToken(String userName,
                                      Calendar creationDate,
                                      int expiresIn,
                                      String tokenType,
                                      String accessToken)
            throws PersistenceException {
        try {
            UserDTO userDTO =
                    getUser(userName);
            UserAccessTokenDTO userAccessTokenDTO =
                    userDTO.getUserAccessTokenDTO() == null ?
                            new UserAccessTokenDTO() :
                            userDTO.
                                    getUserAccessTokenDTO();

            userAccessTokenDTO.
                    setCreationDate(creationDate).
                    setExpiresIn(expiresIn).
                    setTokenType(tokenType).
                    setAccessToken(accessToken);

            userDTO.
                    setUserAccessTokenDTO(
                            userAccessTokenDTO);

            entityManager.
                    merge(userDTO);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
