package com.livewall.lawwalletfinalyearproject.ModelClass;

public class BookedModelClass {
    String lawyeruid,lawyername,lawyerimage;

    public BookedModelClass() {
    }

    public BookedModelClass(String lawyeruid, String lawyername, String lawyerimage) {
        this.lawyeruid = lawyeruid;
        this.lawyername = lawyername;
        this.lawyerimage = lawyerimage;
    }

    public String getLawyeruid() {
        return lawyeruid;
    }

    public void setLawyeruid(String lawyeruid) {
        this.lawyeruid = lawyeruid;
    }

    public String getLawyername() {
        return lawyername;
    }

    public void setLawyername(String lawyername) {
        this.lawyername = lawyername;
    }

    public String getLawyerimage() {
        return lawyerimage;
    }

    public void setLawyerimage(String lawyerimage) {
        this.lawyerimage = lawyerimage;
    }
}
