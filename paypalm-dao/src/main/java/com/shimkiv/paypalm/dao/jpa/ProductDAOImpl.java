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

import com.shimkiv.paypalm.dao.ProductDAO;
import com.shimkiv.paypalm.dto.ProductDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Product DAO JPA Implementation
 *
 * @author Serhii Shymkiv
 */

public class ProductDAOImpl extends BaseJPADAO implements ProductDAO {
    public ProductDAOImpl() {
        super();
    }

    public ProductDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public ProductDTO getProduct(int id)
            throws PersistenceException {
        try {
            return entityManager.
                    createQuery(
                            "SELECT p FROM ProductDTO p WHERE p.id = :productId",
                            ProductDTO.class).
                    setParameter("productId", id).
                    getSingleResult();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public List<ProductDTO> getProductsList()
            throws PersistenceException {
        try {
            return entityManager.
                    createQuery(
                            "SELECT p FROM ProductDTO p",
                            ProductDTO.class).
                    getResultList();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
