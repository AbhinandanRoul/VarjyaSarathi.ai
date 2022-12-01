package com.spp.varjyasarathi;

import java.io.Serializable;

public class Requests implements Serializable {
    public String id;
    public String userLat;
    public String userLong;
    public String empLat;
    public String empLong;
    public String userId;
    public String empId;
    public String status;
    public double price = 0;
    public double distance =0 ;
    public Requests(){
    }

    public Requests(String id, String userLat, String userLong, String empLat, String empLong, String userId, String empId, String status) {
        this.id = id;
        this.userLat = userLat;
        this.userLong = userLong;
        this.empLat = empLat;
        this.empLong = empLong;
        this.userId = userId;
        this.empId = empId;
        this.status = status;
    }

    public Requests(String id, String userLat, String userLong, String empLat, String empLong, String userId, String empId, String status, double price, double distance) {
        this.id = id;
        this.userLat = userLat;
        this.userLong = userLong;
        this.empLat = empLat;
        this.empLong = empLong;
        this.userId = userId;
        this.empId = empId;
        this.status = status;
        this.price = price;
        this.distance = distance;
    }
}
