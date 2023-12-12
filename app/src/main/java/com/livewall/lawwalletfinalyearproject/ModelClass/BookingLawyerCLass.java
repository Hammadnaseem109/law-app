package com.livewall.lawwalletfinalyearproject.ModelClass;

public class BookingLawyerCLass {
    String lawyerID,name,image;

    public BookingLawyerCLass(String lawyerID, String name, String image) {
        this.lawyerID = lawyerID;
        this.name = name;
        this.image = image;
    }

    public BookingLawyerCLass() {
    }

    public String getLawyerID() {
        return lawyerID;
    }

    public void setLawyerID(String lawyerID) {
        this.lawyerID = lawyerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
