/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxicalls.billing.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author romulo
 */
@Entity
@NamedQuery(name = "Billing.findAll", query = "SELECT b FROM Billing b")
public class Billing implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private final Date createdTime;

    private String fromEntity;
    private Long fromId;
    private String toEntity;
    private Long toId;

    private Double price;

    public Billing() {
        this.createdTime = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFromEntity(String fromEntity) {
        this.fromEntity = fromEntity;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public void setToEntity(String toEntity) {
        this.toEntity = toEntity;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
