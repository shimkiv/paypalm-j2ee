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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * JPA Order DTO
 *
 * @author Serhii Shymkiv
 */

@Entity
@Table(name = "`order`")
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "paypal_id")
    @Size(max = 128)
    private String paypalId;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar creationDate;

    @Column(name = "card_number")
    @Size(max = 100)
    private String cardNumber;

    @Column(name = "card_type")
    @Size(max = 50)
    private String cardType;

    @Column(name = "amount")
    @Digits(integer = 5, fraction = 2)
    private Double amount;

    @Column(name = "status")
    @Size(max = 20)
    private String status;

    @Column(name = "link")
    @Size(max = 256)
    private String link;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="order_products",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")}
    )
    private List<ProductDTO> productDTOList =
            new ArrayList<>();

    public OrderDTO() {
        //
    }

    public int getId() {
        return id;
    }

    public OrderDTO setId(int id) {
        this.id = id;

        return this;
    }

    public int getUserId() {
        return userId;
    }

    public OrderDTO setUserId(int userId) {
        this.userId = userId;

        return this;
    }

    public String getPaypalId() {
        return paypalId;
    }

    public OrderDTO setPaypalId(String paypalId) {
        this.paypalId = paypalId;

        return this;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public OrderDTO setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;

        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public OrderDTO setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;

        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public OrderDTO setCardType(String cardType) {
        this.cardType = cardType;

        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public OrderDTO setAmount(Double amount) {
        this.amount = amount;

        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderDTO setStatus(String status) {
        this.status = status;

        return this;
    }

    public String getLink() {
        return link;
    }

    public OrderDTO setLink(String link) {
        this.link = link;

        return this;
    }

    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public OrderDTO setProductDTOList(List<ProductDTO>
                                              productDTOList) {
        this.productDTOList = productDTOList;

        return this;
    }
}
