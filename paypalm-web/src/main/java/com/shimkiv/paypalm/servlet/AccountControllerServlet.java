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

package com.shimkiv.paypalm.servlet;

import com.shimkiv.paypalm.dto.OrderDTO;
import com.shimkiv.paypalm.dto.ProductDTO;
import com.shimkiv.paypalm.dto.UserAccessTokenDTO;
import com.shimkiv.paypalm.ejb.*;
import com.shimkiv.paypalm.vo.paypal.PaymentResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.shimkiv.paypalm.crypto.Crypter.encrypt;
import static com.shimkiv.paypalm.enums.PaymentResponseState.FAILED;
import static com.shimkiv.paypalm.properties.PropertiesManager.getPropertyValue;
import static com.shimkiv.paypalm.utils.CommonUtils.*;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Account Controller Servlet
 *
 * @author Serhii Shymkiv
 */

@SuppressWarnings({
        "CdiInjectionPointsInspection",
        "SpringAutowiredFieldsWarningInspection"
})
public class AccountControllerServlet extends HttpServlet {
    private static final Logger LOG =
            LoggerFactory.getLogger(AccountControllerServlet.class);

    @EJB
    private UserBean userBean;

    @EJB
    private ProductBean productBean;

    @EJB
    private OrderBean orderBean;

    @EJB
    private PayPalBean payPalBean;

    @Inject
    private ShoppingCartBean shoppingCartBean;

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo =
                StringUtils.isNotBlank(
                        request.
                                getPathInfo()) ?
                        request.
                                getPathInfo() :
                        "/";

        setUserEntityAttribute(
                request);

        if(!pathInfo.
                startsWith("/statistics")) {
            switch(pathInfo) {
                case "/update":
                    processUpdateRequest(
                            request,
                            response);

                    break;
                case "/clear-shopping-cart":
                    processClearShoppingCartBeanRequest();

                    break;
                case "/update-shopping-cart":
                    processUpdateShoppingCartBeanRequest(
                            request);

                    break;
                case "/checkout":
                    processCheckoutRequest(
                            request,
                            response);

                    break;
                case "/pay":
                    processPaymentRequest(
                            request,
                            response);

                    break;
                default:
                    processIndexRequest(
                            request,
                            response);

                    break;
            }
        } else {
            processStatisticsRequest(
                    request,
                    response);
        }
    }

    private void processIndexRequest(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        setProductDTOListAttribute(
                request,
                false);
        setShoppingCartBeanAttribute(
                request);
        setCurrencyAttribute(
                request);

        getServletContext().
                getRequestDispatcher(
                        getPropertyValue(
                                "account.index.jsp.path")).
                forward(request, response);
    }

    private void processUpdateRequest(HttpServletRequest request,
                                      HttpServletResponse response)
            throws ServletException, IOException {
        try {
            userBean.updateUserSettings(
                    getAuthorizedUserName(),
                    request.getParameter(
                            getPropertyValue("user.first.name.param")).
                            trim(),
                    request.getParameter(
                            getPropertyValue("user.last.name.param")).
                            trim(),
                    request.getParameter(
                            getPropertyValue("user.email.param")).
                            trim(),
                    request.getParameter(
                            getPropertyValue("user.pay.pal.client.id.param")).
                            trim(),
                    request.getParameter(
                            getPropertyValue("user.pay.pal.secret.phrase.param")).
                            trim());
        } catch (Exception e) {
            processError(
                    request,
                    response,
                    e);
        }

        response.sendRedirect(
                request.getContextPath() +
                        getPropertyValue(
                                "account.index.path"));
    }

    private void processClearShoppingCartBeanRequest() {
        shoppingCartBean.
                clear();
    }

    private void processUpdateShoppingCartBeanRequest(HttpServletRequest request) {
        int productId =
                uniqueRefToPkey(
                        request.getParameter(
                                getPropertyValue("product.param")));
        ProductDTO productDTO =
                productBean.
                        getProduct(productId);
        double productPrice =
                productDTO != null ?
                        productDTO.
                                getPrice() :
                        0.00;

        if(Boolean.parseBoolean(
                request.getParameter(
                        getPropertyValue("status.param")))) {
            shoppingCartBean.
                    addProduct(
                            productId,
                            productPrice);
        } else {
            shoppingCartBean.
                    removeProduct(
                            productId,
                            productPrice);
        }
    }

    private void processCheckoutRequest(HttpServletRequest request,
                                        HttpServletResponse response)
            throws ServletException, IOException {
        setProductDTOListAttribute(
                request,
                true);
        setShoppingCartBeanAttribute(
                request);
        setCurrencyAttribute(
                request);

        getServletContext().
                getRequestDispatcher(
                        getPropertyValue(
                                "account.checkout.jsp.path")).
                forward(request, response);
    }

    private void processPaymentRequest(HttpServletRequest request,
                                       HttpServletResponse response)
            throws ServletException, IOException {
        try {
            NumberFormat numberFormat =
                    getNumberFormat();
            UserAccessTokenDTO userAccessTokenDTO =
                    payPalBean.
                            getUserAccessToken();
            PaymentResponse paymentResponse =
                    payPalBean.
                            performPayPalPaymentRequest(
                                    userAccessTokenDTO.
                                            getAccessToken(),
                                    userAccessTokenDTO.
                                            getTokenType(),
                                    generatePayPalPayment(
                                            request));
            String jsonResponse =
                    GSON.toJson(
                            paymentResponse,
                            PaymentResponse.class);
            OrderDTO orderDTO =
                    new OrderDTO();

            if(paymentResponse.getState().
                    equalsIgnoreCase(FAILED.getState())) {
                LOG.info("PAYMENT FAIL RESPONSE: {}",
                        jsonResponse);
            } else {
                if(collectionIsNotEmpty(
                        paymentResponse.
                                getLinks())) {
                    orderDTO.setLink(
                            paymentResponse.
                                    getLinks().
                                    get(FIRST_ELEMENT).
                                    getHref());
                }
            }

            orderDTO.
                    setUserId(
                            userBean.
                                    getUser(
                                            getAuthorizedUserName()).
                                    getId()).
                    setPaypalId(
                            paymentResponse.
                                    getId()).
                    setCreationDate(
                            Calendar.
                                    getInstance()).
                    setCardNumber(
                            encrypt(request.getParameter(
                                    getPropertyValue("card.number.param")).
                                    trim())).
                    setCardType(
                            request.getParameter(
                                    getPropertyValue("card.type.param")).
                                    trim()).
                    setAmount(
                            Double.parseDouble(
                                    numberFormat.format(
                                            shoppingCartBean.
                                                    getTotalAmount()))).
                    setStatus(
                            paymentResponse.
                                    getState()).
                    setProductDTOList(
                            getProductDTOListOfShoppingCart());

            orderBean.
                    persistOrder(
                            orderDTO);

            response.setContentType(APPLICATION_JSON);
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            processError(
                    request,
                    response,
                    e);
        } finally {
            shoppingCartBean.
                    clear();
        }
    }

    private void processStatisticsRequest(HttpServletRequest request,
                                          HttpServletResponse response)
            throws ServletException, IOException {
        int productId =
                uniqueRefToPkey(
                        request.getParameter(
                                getPropertyValue("product.param")));

        setProductDTOAttribute(
                request,
                productId);
        setOrderDTOListAttribute(
                request,
                productId);
        setCurrencyAttribute(
                request);

        getServletContext().
                getRequestDispatcher(
                        getPropertyValue(
                                "account.statistics.jsp.path")).
                forward(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response,
                              Exception exception)
            throws ServletException, IOException {
        request.setAttribute("javax.servlet.error.exception",
                exception);
        request.setAttribute("javax.servlet.error.status_code",
                500);
        request.setAttribute("javax.servlet.error.request_uri",
                request.getRequestURI());

        getServletContext().
                getRequestDispatcher(
                        getPropertyValue(
                                "error.path")).
                forward(request, response);
    }

    private void setUserEntityAttribute(HttpServletRequest request) {
        try {
            request.setAttribute(
                    getPropertyValue("user.dto.attr.name"),
                    userBean.getUser(
                            getAuthorizedUserName()));
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }
    }

    private void setProductDTOListAttribute(HttpServletRequest request,
                                            boolean isForCheckingOut) {
        try {
            if(!isForCheckingOut) {
                request.setAttribute(
                        getPropertyValue("product.dto.list.attr.name"),
                        productBean.
                                getProductsList());
            } else {
                request.setAttribute(
                        getPropertyValue("product.dto.list.attr.name"),
                        getProductDTOListOfShoppingCart());
            }
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }
    }

    private void setProductDTOAttribute(HttpServletRequest request,
                                        int productId) {
        try {
            request.setAttribute(
                    getPropertyValue("product.dto.attr.name"),
                    productBean.
                            getProduct(productId));
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }
    }

    private void setOrderDTOListAttribute(HttpServletRequest request,
                                          int productId) {
        try {
            request.setAttribute(
                    getPropertyValue("order.dto.list.attr.name"),
                    orderBean.
                            getProductRelatedOrders(
                                    userBean.
                                            getUser(
                                                    getAuthorizedUserName()).
                                            getId(),
                                    productId));
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }
    }

    private void setShoppingCartBeanAttribute(HttpServletRequest request) {
        try {
            request.setAttribute(
                    getPropertyValue("shopping.cart.bean.attr.name"),
                    shoppingCartBean);
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }
    }

    private void setCurrencyAttribute(HttpServletRequest request) {
        try {
            request.setAttribute(
                    getPropertyValue("currency.attr.name"),
                    CURRENCY);
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);
        }
    }

    private List<ProductDTO> getProductDTOListOfShoppingCart() {
        List<ProductDTO> productDTOList =
                new ArrayList<>();

        for(int productId :
                shoppingCartBean.
                        getProductEntities()) {
            ProductDTO productDTO =
                    productBean.
                            getProduct(
                                    productId);

            if(productDTO != null) {
                productDTOList.
                        add(productDTO);
            }
        }

        return productDTOList;
    }

    private String generatePayPalPayment(HttpServletRequest request) {
        return payPalBean.
                generatePayPalPaymentPayload(
                        request.getParameter(
                                getPropertyValue("card.type.param")).
                                trim(),
                        request.getParameter(
                                getPropertyValue("card.number.param")).
                                trim(),
                        request.getParameter(
                                getPropertyValue("card.exp.month.param")).
                                trim(),
                        request.getParameter(
                                getPropertyValue("card.exp.year.param")).
                                trim(),
                        request.getParameter(
                                getPropertyValue("card.cvv.param")).
                                trim(),
                        request.getParameter(
                                getPropertyValue("card.name.param")).
                                trim(),
                        String.valueOf(
                                shoppingCartBean.
                                        getTotalAmount()));
    }
}
