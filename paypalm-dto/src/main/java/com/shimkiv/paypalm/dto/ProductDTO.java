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

package com.shimkiv.paypalm.dto;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * JPA Product DTO
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "product")
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    @Size(max = 50)
    private String name;

    @Column(name = "description")
    @Size(max = 256)
    private String description;

    @Column(name = "price")
    @Digits(integer = 5, fraction = 2)
    private Double price;

    public ProductDTO() {
        //
    }

    public int getId() {
        return id;
    }

    public ProductDTO setId(int id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDTO setName(String name) {
        this.name = name;

        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDTO setDescription(String description) {
        this.description = description;

        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductDTO setPrice(Double price) {
        this.price = price;

        return this;
    }
}
