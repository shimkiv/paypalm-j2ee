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

import com.shimkiv.paypalm.dto.OrderDTO;

import java.util.List;

/**
 * Order DAO Interface
 *
 * @author Serhii Shymkiv
 */

public interface OrderDAO {
    void persistOrder(OrderDTO orderDTO);
    List<OrderDTO> getProductRelatedOrders(int userId,
                                           int productId);
}
