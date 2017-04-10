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

import com.shimkiv.paypalm.dao.ProductDAO;
import com.shimkiv.paypalm.dao.jpa.ProductDAOImpl;
import com.shimkiv.paypalm.dto.ProductDTO;
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
 * Products management Bean
 *
 * @author Serhii Shymkiv
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ProductBean {
    private static final Logger LOG =
            LoggerFactory.getLogger(ProductBean.class);

    @PersistenceContext(unitName = "paypalmPU")
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    private ProductDAO productDAO;

    @PostConstruct
    private void init() {
        this.productDAO =
                new ProductDAOImpl(
                        entityManager);
    }

    /**
     * Gets {@link ProductDTO} by product Id
     *
     * @param productId Product Id
     * @return {@link ProductDTO}
     */
    public ProductDTO getProduct(int productId) {
        try {
            userTransaction.
                    begin();

            ProductDTO productDTO =
                    productDAO.
                            getProduct(
                                    productId);

            userTransaction.
                    commit();

            return productDTO;
        } catch (Exception e) {
            LOG.debug(COMMON_ERROR_MSG, e);

            return null;
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
     * Gets {@link List} of {@link ProductDTO}
     *
     * @return {@link List} of {@link ProductDTO}
     */
    public List<ProductDTO> getProductsList() {
        try {
            userTransaction.
                    begin();

            List<ProductDTO> productDTOList =
                    productDAO.
                            getProductsList();

            userTransaction.
                    commit();

            return productDTOList;
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
