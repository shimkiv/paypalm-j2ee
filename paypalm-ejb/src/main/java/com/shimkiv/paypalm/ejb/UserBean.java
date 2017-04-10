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

package com.shimkiv.paypalm.ejb;

import com.shimkiv.paypalm.dao.UserDAO;
import com.shimkiv.paypalm.dao.jpa.UserDAOImpl;
import com.shimkiv.paypalm.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import java.util.Calendar;

import static com.shimkiv.paypalm.utils.CommonUtils.COMMON_ERROR_MSG;
import static com.shimkiv.paypalm.utils.CommonUtils.COMMON_TXN_ERROR_MSG;

/**
 * Users management Bean
 *
 * @author Serhii Shymkiv
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class UserBean {
    private static final Logger LOG =
            LoggerFactory.getLogger(UserBean.class);

    @PersistenceContext(unitName = "paypalmPU")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    private UserDAO userDAO;

    @PostConstruct
    private void init() {
        this.userDAO =
                new UserDAOImpl(
                        entityManager);
    }

    /**
     * Gets {@link UserDTO} by user name
     *
     * @param userName User name
     * @return {@link UserDTO}
     */
    public UserDTO getUser(String userName) {
        try {
            userTransaction.
                    begin();

            UserDTO userDTO =
                    userDAO.
                            getUser(userName);

            userTransaction.
                    commit();

            return userDTO;
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);

            return new UserDTO();
        } finally {
            try {
                if(userTransaction.getStatus() ==
                        Status.STATUS_ACTIVE) {
                    userTransaction.
                            rollback();
                }
            } catch( Exception exception) {
                LOG.error(COMMON_TXN_ERROR_MSG,
                        exception);
            }
        }
    }

    /**
     * Updates user settings
     *
     * @param userName User name
     * @param firstName User first name
     * @param lastName User last name
     * @param email User email
     * @param payPalClientId User PayPal client Id
     * @param payPalSecretPhrase User PayPal secret phrase
     */
    public void updateUserSettings(String userName,
                                   String firstName,
                                   String lastName,
                                   String email,
                                   String payPalClientId,
                                   String payPalSecretPhrase) {
        try {
            userTransaction.
                    begin();

            userDAO.
                    updateUserSettings(
                            userName,
                            firstName,
                            lastName,
                            email,
                            payPalClientId,
                            payPalSecretPhrase);

            userTransaction.
                    commit();
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        } finally {
            try {
                if(userTransaction.getStatus() ==
                        Status.STATUS_ACTIVE) {
                    userTransaction.
                            rollback();
                }
            } catch( Exception exception) {
                LOG.error(COMMON_TXN_ERROR_MSG,
                        exception);
            }
        }
    }

    /**
     * Updates user access token
     *
     * @param userName User name
     * @param creationDate Access token creation date
     * @param expiresIn Access token expiration
     * @param tokenType Access token type
     * @param accessToken Access token
     */
    public void updateUserAccessToken(String userName,
                                      Calendar creationDate,
                                      int expiresIn,
                                      String tokenType,
                                      String accessToken) {
        try {
            userTransaction.
                    begin();

            userDAO.
                    updateUserAccessToken(
                            userName,
                            creationDate,
                            expiresIn,
                            tokenType,
                            accessToken);

            userTransaction.
                    commit();
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        } finally {
            try {
                if(userTransaction.getStatus() ==
                        Status.STATUS_ACTIVE) {
                    userTransaction.
                            rollback();
                }
            } catch( Exception exception) {
                LOG.error(COMMON_TXN_ERROR_MSG,
                        exception);
            }
        }
    }
}
