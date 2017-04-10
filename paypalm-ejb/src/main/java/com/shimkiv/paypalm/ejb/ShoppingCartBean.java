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

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Shopping Cart Bean
 *
 * @author Serhii Shymkiv
 */

@SessionScoped
public class ShoppingCartBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Set<Integer> productEntities;
    private double totalAmount = 0.00;

    public ShoppingCartBean() {
        this.productEntities =
                new HashSet<>();
    }

    public Set<Integer> getProductEntities() {
        return productEntities;
    }

    public void setProductEntities(Set<Integer>
                                           productEntities) {
        this.productEntities =
                productEntities;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean contains(int id) {
        return this.productEntities.
                contains(id);
    }

    public void addProduct(int id, double price) {
        this.productEntities.add(id);
        this.totalAmount += price;
    }

    public void removeProduct(int id, double price) {
        this.productEntities.remove(id);

        if(this.totalAmount > 0) {
            this.totalAmount -= price;
        }
    }

    public void clear() {
        this.productEntities.clear();
        this.totalAmount = 0.00;
    }

    public int size() {
        return this.productEntities.size();
    }
}
