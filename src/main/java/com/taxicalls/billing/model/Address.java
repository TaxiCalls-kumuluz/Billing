/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.taxicalls.billing.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author romulo
 */
public class Address implements Serializable {

    private Long id;
    private Long longitude;
    private Long latitude;

    public Address() {
    }

    public Coordinate getCoordinate() {
        return new Coordinate(longitude, latitude);
    }

    public void setCoordinate(Coordinate coordinate) {
        this.latitude = coordinate.getLatitude();
        this.longitude = coordinate.getLongitude();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }
        Address other = (Address) obj;
        return Objects.equals(this.getId(), other.getId());
    }
}
