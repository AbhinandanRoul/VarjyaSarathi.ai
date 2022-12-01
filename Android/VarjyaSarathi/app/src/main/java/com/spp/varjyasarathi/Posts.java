package com.spp.varjyasarathi;

import java.io.Serializable;

public class Posts implements Serializable {
    public String id;
    public String dustbinId;
    public String username;
    public String dustbinColor;
    public Double confidence;
    public String lattitude;
    public String longitude;
    public String userId;
    public int valiance=0;
    public int comments = 0;
    public Posts(){
    }

    public Posts(String id, String dustbinId, String username, String dustbinColor, Double confidence, String lattitude, String longitude, String userId, int valiance, int comments) {
        this.id = id;
        this.dustbinId = dustbinId;
        this.username = username;
        this.dustbinColor = dustbinColor;
        this.confidence = confidence;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.userId = userId;
        this.valiance = valiance;
        this.comments = comments;
    }
}
