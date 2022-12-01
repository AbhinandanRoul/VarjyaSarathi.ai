package com.spp.varjyasarathi;

import java.io.Serializable;

public class Users implements Serializable  {
    public String personName;
    public String personGivenName;
    public String personFamilyName;
    public String personEmail;
    public String personId;
    public String personPhoto;
    public String posts = "";
    public int vsp = 0;
    public String requestStatus="";

    public Users(){
    }
    public Users(String personName, String personGivenName, String personFamilyName, String personEmail, String personId , String personPhoto) {
        this.personName = personName;
        this.personGivenName = personGivenName;
        this.personFamilyName = personFamilyName;
        this.personEmail = personEmail;
        this.personId = personId;
        this.personPhoto = personPhoto;
    }

    public Users(String personName, String personGivenName, String personFamilyName, String personEmail, String personId, String personPhoto, int vsp) {
        this.personName = personName;
        this.personGivenName = personGivenName;
        this.personFamilyName = personFamilyName;
        this.personEmail = personEmail;
        this.personId = personId;
        this.personPhoto = personPhoto;
        this.vsp = vsp;
    }

    public Users(String personName, String personGivenName, String personFamilyName, String personEmail, String personId, String personPhoto, String posts, int vsp, String requestStatus) {
        this.personName = personName;
        this.personGivenName = personGivenName;
        this.personFamilyName = personFamilyName;
        this.personEmail = personEmail;
        this.personId = personId;
        this.personPhoto = personPhoto;
        this.posts = posts;
        this.vsp = vsp;
        this.requestStatus = requestStatus;
    }

    public void setPosts(String posts){
        this.posts = posts;
    }
}
