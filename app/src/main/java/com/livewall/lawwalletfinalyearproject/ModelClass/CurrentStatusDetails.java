package com.livewall.lawwalletfinalyearproject.ModelClass;

public class CurrentStatusDetails {
    String UerID,curentstatus,Fullname,Email,Passowrd;

    public CurrentStatusDetails(String uerID, String curentstatus, String fullname, String email, String passowrd) {
        UerID = uerID;
        this.curentstatus = curentstatus;
        Fullname = fullname;
        Email = email;
        Passowrd = passowrd;
    }

    public CurrentStatusDetails() {

    }

    public String getUerID() {
        return UerID;
    }

    public void setUerID(String uerID) {
        UerID = uerID;
    }

    public String getCurentstatus() {
        return curentstatus;
    }

    public void setCurentstatus(String curentstatus) {
        this.curentstatus = curentstatus;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassowrd() {
        return Passowrd;
    }

    public void setPassowrd(String passowrd) {
        Passowrd = passowrd;
    }
}
