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

import com.shimkiv.paypalm.dao.OrderDAO;
import com.shimkiv.paypalm.dao.jpa.OrderDAOImpl;
import com.shimkiv.paypalm.dto.OrderDTO;
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
import java.util.Collections;
import java.util.List;

import static com.shimkiv.paypalm.utils.CommonUtils.COMMON_ERROR_MSG;
import static com.shimkiv.paypalm.utils.CommonUtils.COMMON_TXN_ERROR_MSG;

/**
 * Orders management Bean
 *
 * @author Serhii Shymkiv
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class OrderBean {
    private static final Logger LOG =
            LoggerFactory.getLogger(OrderBean.class);

    @PersistenceContext(unitName = "paypalmPU")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    private OrderDAO orderDAO;

    @PostConstruct
    private void init() {
        this.orderDAO =
                new OrderDAOImpl(
                        entityManager);
    }

    /**
     * Persists {@link OrderDTO}
     *
     * @param orderDTO {@link OrderDTO} to persist
     */
    public void persistOrder(OrderDTO orderDTO) {
        try {
            userTransaction.
                    begin();

            orderDAO.
                    persistOrder(orderDTO);

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
     * Gets {@link List} of {@link OrderDTO}
     *
     * @param userId User Id
     * @param productId Product Id
     * @return {@link List} of {@link OrderDTO}
     */
    public List<OrderDTO> getProductRelatedOrders(int userId,
                                                  int productId) {
        try {
            userTransaction.
                    begin();

            List<OrderDTO> orderDTOList =
                    orderDAO.
                            getProductRelatedOrders(
                                    userId,
                                    productId);

            userTransaction.
                    commit();

            return orderDTOList;
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);

            return Collections.
                    emptyList();
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
