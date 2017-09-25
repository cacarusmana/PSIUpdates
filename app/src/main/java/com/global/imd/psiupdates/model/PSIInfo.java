package com.global.imd.psiupdates.model;

import java.io.Serializable;

/**
 * Created by Caca Rusmana on 18/09/2017.
 */

public class PSIInfo implements Serializable {

    private static final long serialVersionUID = -2780968318446526518L;

    private String name;
    private double longitude;
    private double latitude;
    private Integer value;


    public PSIInfo(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
