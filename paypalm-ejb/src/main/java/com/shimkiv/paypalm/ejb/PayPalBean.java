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

import com.shimkiv.paypalm.dto.UserAccessTokenDTO;
import com.shimkiv.paypalm.dto.UserDTO;
import com.shimkiv.paypalm.dto.UserSettingsDTO;
import com.shimkiv.paypalm.vo.paypal.*;
import com.shimkiv.paypalm.vo.paypal.impl.CreditCard;
import com.shimkiv.paypalm.vo.paypal.impl.CreditCardWrapper;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.shimkiv.paypalm.enums.PaymentMethod.CREDIT_CARD;
import static com.shimkiv.paypalm.enums.PaymentResponseState.FAILED;
import static com.shimkiv.paypalm.enums.PaymentType.SALE;
import static com.shimkiv.paypalm.properties.PropertiesManager.getPropertyValue;
import static com.shimkiv.paypalm.utils.CommonUtils.*;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME;

/**
 * PayPal integration management Bean
 *
 * @author Serhii Shymkiv
 */

@Stateless
public class PayPalBean {
    private static final Logger LOG =
            LoggerFactory.getLogger(PayPalBean.class);

    @EJB
    private UserBean userBean;

    /**
     * Generates PayPal Payment Request JSON payload
     *
     * @param cardType Card type
     * @param cardNumber Card number
     * @param expMonth Card expiration month
     * @param expYear Card expiration year
     * @param cvv Card CVV
     * @param name Name on card
     * @param totalAmount Total amount
     * @return PayPal Payment Request JSON payload
     */
    public String generatePayPalPaymentPayload(String cardType,
                                               String cardNumber,
                                               String expMonth,
                                               String expYear,
                                               String cvv,
                                               String name,
                                               String totalAmount) {
        if(StringUtils.
                isNoneBlank(cardType, cardNumber,
                        expMonth, expYear,
                        cvv, name, totalAmount)) {
            List<FundingInstrument> fundingInstruments =
                    new ArrayList<>();
            List<Transaction> transactions =
                    new ArrayList<>();
            String firstName =
                    name.split("\\s")[0];
            String lastName =
                    name.split("\\s").length > 1 ?
                            name.split("\\s")[1] :
                            EMPTY;
            NumberFormat numberFormat =
                    getNumberFormat();

            fundingInstruments.add(new CreditCardWrapper().
                    setCreditCard(new CreditCard().
                            setNumber(cardNumber).
                            setType(cardType).
                            setExpireMonth(expMonth).
                            setExpireYear(expYear).
                            setCvv2(cvv).
                            setFirstName(firstName).
                            setLastName(lastName)));
            transactions.add(new Transaction().
                    setAmount(
                            new Amount().
                                    setTotal(
                                            numberFormat.format(
                                                    Double.parseDouble(
                                                            totalAmount))).
                                    setCurrency(
                                            CURRENCY.
                                                    toString())));

            return GSON.toJson(
                    new PaymentRequest().
                            setIntent(SALE.getType()).
                            setPayer(new Payer().
                                    setPaymentMethod(
                                            CREDIT_CARD.
                                                    getMethod()).
                                    setFundingInstruments(
                                            fundingInstruments)).
                            setTransactions(
                                    transactions));
        }

        return EMPTY;
    }

    /**
     * Gets {@link UserAccessTokenDTO}
     *
     * @return {@link UserAccessTokenDTO}
     */
    public UserAccessTokenDTO getUserAccessToken() {
        UserDTO userDTO =
                userBean.getUser(
                        getAuthorizedUserName());
        UserSettingsDTO userSettingsDTO =
                userDTO.
                        getUserSettingsDTO();
        UserAccessTokenDTO userAccessTokenDTO =
                userDTO.
                        getUserAccessTokenDTO();
        Calendar currentDate;

        if(userAccessTokenDTO != null &&
                isValidUserAccessToken(
                        userAccessTokenDTO)) {
            return userAccessTokenDTO;
        } else {
            AccessTokenResponse accessTokenResponse =
                    getNewAccessToken(
                            userSettingsDTO.
                                    getPayPalClientId(),
                            userSettingsDTO.
                                    getPayPalSecretPhrase());

            if(accessTokenResponse == null) {
                throw new RuntimeException(
                        COMMON_ERROR_MSG);
            }

            currentDate =
                    Calendar.
                            getInstance();

            userBean.updateUserAccessToken(
                    getAuthorizedUserName(),
                    currentDate,
                    accessTokenResponse.
                            getExpiresIn(),
                    accessTokenResponse.
                            getTokenType(),
                    accessTokenResponse.
                            getAccessToken());

            return userBean.
                    getUser(getAuthorizedUserName()).
                    getUserAccessTokenDTO();
        }
    }

    /**
     * Performs PayPal Payment request
     *
     * @param accessToken PayPalm access (OAuth) token
     * @param accessTokenType PayPalm access (OAuth) token type
     * @param payLoad Payment JSON payload
     * @return {@link PaymentResponse}
     */
    public PaymentResponse performPayPalPaymentRequest(String accessToken,
                                                       String accessTokenType,
                                                       String payLoad) {
        if(StringUtils.isNoneBlank(
                accessToken, accessTokenType, payLoad)) {
            Response response =
                    ClientBuilder.newClient().
                            property(ClientProperties.
                                    CONNECT_TIMEOUT, 0).
                            property(ClientProperties.
                                    READ_TIMEOUT, 0).
                            target(getPropertyValue("paypal.payment.api.rest.endpoint")).
                            request(APPLICATION_JSON_TYPE).
                            header(AUTHORIZATION,
                                    composeAccessToken(
                                            accessToken,
                                            accessTokenType)).
                            post(Entity.entity(
                                    payLoad,
                                    APPLICATION_JSON_TYPE),
                                    Response.class);
            String responseJson =
                    response.readEntity(
                            String.class);

            if(response.getStatus() == HTTP_OK ||
                    response.getStatus() == HTTP_CREATED) {
                return GSON.fromJson(
                        responseJson,
                        PaymentResponse.class);
            } else {
                LOG.debug(COMMON_ERROR_MSG);

                return new PaymentResponse().
                        setState(FAILED.getState()).
                        setErrorResponse(
                                StringUtils.isNotBlank(responseJson) &&
                                        !responseJson.contains(
                                                getPropertyValue("read.time.out.msg")) ?
                                        GSON.fromJson(
                                                responseJson,
                                                ErrorResponse.class) :
                                        null);
            }
        }

        LOG.debug(COMMON_ERROR_MSG);

        return new PaymentResponse().
                setState(FAILED.getState());
    }

    private AccessTokenResponse getNewAccessToken(String payPalClientId,
                                                  String payPalSecretPhrase) {
        if(StringUtils.isNoneBlank(
                payPalClientId, payPalSecretPhrase)) {
            MultivaluedHashMap<String, String> multivaluedHashMap =
                    new MultivaluedHashMap<>();
            multivaluedHashMap.add(
                    getPropertyValue("paypal.oauth.request.body.key"),
                    getPropertyValue("paypal.oauth.request.body.value"));

            Response response =
                    ClientBuilder.newClient().
                            register(HttpAuthenticationFeature.
                                    basicBuilder().
                                    build()).
                            property(ClientProperties.
                                    CONNECT_TIMEOUT, 0).
                            property(ClientProperties.
                                    READ_TIMEOUT, 0).
                            target(getPropertyValue("paypal.oauth.api.rest.endpoint")).
                            property(HTTP_AUTHENTICATION_BASIC_USERNAME,
                                    payPalClientId).
                            property(HTTP_AUTHENTICATION_BASIC_PASSWORD,
                                    payPalSecretPhrase).
                            request(APPLICATION_JSON_TYPE).
                            header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_TYPE).
                            post(Entity.form(multivaluedHashMap),
                                    Response.class);
            String responseJson =
                    response.readEntity(
                            String.class);

            if(response.getStatus() == HTTP_OK) {
                return GSON.fromJson(
                        responseJson,
                        AccessTokenResponse.class);
            } else {
                LOG.debug(COMMON_ERROR_MSG);
                LOG.debug(responseJson);

                return null;
            }
        }

        LOG.debug(COMMON_ERROR_MSG);

        return null;
    }

    private String composeAccessToken(String accessToken,
                                      String accessTokenType) {
        return accessTokenType + " " + accessToken;
    }

    private boolean isValidUserAccessToken(UserAccessTokenDTO
                                                   userAccessTokenDTO) {
        try {
            Calendar futureDateTime =
                    userAccessTokenDTO.
                            getCreationDate();
            futureDateTime.add(
                    Calendar.SECOND,
                    userAccessTokenDTO.
                            getExpiresIn());
            futureDateTime.set(Calendar.SECOND, 0);
            futureDateTime.set(Calendar.MILLISECOND, 0);

            Calendar currentDateTime =
                    Calendar.getInstance();
            currentDateTime.set(Calendar.SECOND, 0);
            currentDateTime.set(Calendar.MILLISECOND, 0);

            return currentDateTime.
                    before(futureDateTime);
        } catch (Exception e) {
            return false;
        }
    }
}
