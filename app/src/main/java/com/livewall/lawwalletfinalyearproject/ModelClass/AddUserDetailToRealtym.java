package com.livewall.lawwalletfinalyearproject.ModelClass;

public class AddUserDetailToRealtym {
    String userID,name,cnic,imageurl;

    public AddUserDetailToRealtym() {
    }

    public AddUserDetailToRealtym(String userID, String name, String cnic, String imageurl) {
        this.userID = userID;
        this.name = name;
        this.cnic = cnic;
        this.imageurl = imageurl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
