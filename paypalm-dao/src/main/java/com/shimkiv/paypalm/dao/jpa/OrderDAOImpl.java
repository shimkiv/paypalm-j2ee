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

import com.shimkiv.paypalm.dao.OrderDAO;
import com.shimkiv.paypalm.dto.OrderDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Order DAO JPA Implementation
 *
 * @author Serhii Shymkiv
 */

public class OrderDAOImpl extends BaseJPADAO implements OrderDAO {
    public OrderDAOImpl() {
        super();
    }

    public OrderDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void persistOrder(OrderDTO orderDTO)
            throws PersistenceException {
        try {
            entityManager.
                    persist(orderDTO);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<OrderDTO> getProductRelatedOrders(int userId,
                                                  int productId)
            throws PersistenceException {
        try {
            return entityManager.
                    createQuery(
                            "SELECT o FROM OrderDTO o LEFT JOIN o.productDTOList p " +
                                    "WHERE o.userId = :userId AND p.id = :productId",
                            OrderDTO.class).
                    setParameter("userId", userId).
                    setParameter("productId", productId).
                    getResultList();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
