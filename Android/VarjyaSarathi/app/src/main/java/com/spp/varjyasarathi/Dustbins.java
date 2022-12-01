package com.spp.varjyasarathi;

import java.io.Serializable;

public class Dustbins implements Serializable {
    public String DustbinID;
    public String Lat;
    public String Long;
    public String Type;
    public int R_RODs;
    public Dustbins(){
    }

    public Dustbins(String dustbinID, String lat, String aLong, String type, int r_RODs) {
        DustbinID = dustbinID;
        Lat = lat;
        Long = aLong;
        Type = type;
        R_RODs = r_RODs;
    }
}
