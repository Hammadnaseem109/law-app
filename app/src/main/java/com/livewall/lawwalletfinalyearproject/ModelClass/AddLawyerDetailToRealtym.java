package com.livewall.lawwalletfinalyearproject.ModelClass;

public class AddLawyerDetailToRealtym {
    public AddLawyerDetailToRealtym() {
    }

    String currentuid,lawyername,laywernumber,spec,exper,addresses,imageuri,status;

    public AddLawyerDetailToRealtym(String currentuid, String lawyername, String laywernumber, String spec, String exper, String addresses, String imageuri, String status) {
        this.currentuid = currentuid;
        this.lawyername = lawyername;
        this.laywernumber = laywernumber;
        this.spec = spec;
        this.exper = exper;
        this.addresses = addresses;
        this.imageuri = imageuri;
        this.status = status;
    }

    public String getCurrentuid() {
        return currentuid;
    }

    public void setCurrentuid(String currentuid) {
        this.currentuid = currentuid;
    }

    public String getLawyername() {
        return lawyername;
    }

    public void setLawyername(String lawyername) {
        this.lawyername = lawyername;
    }

    public String getLaywernumber() {
        return laywernumber;
    }

    public void setLaywernumber(String laywernumber) {
        this.laywernumber = laywernumber;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getExper() {
        return exper;
    }

    public void setExper(String exper) {
        this.exper = exper;
    }

    public String getAddresses() {
        return addresses;
    }

    public void setAddresses(String addresses) {
        this.addresses = addresses;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
