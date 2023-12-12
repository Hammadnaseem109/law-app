package com.livewall.lawwalletfinalyearproject.ModelClass;

public class SharePdfModelClass {
    String lawyeruid,curentusername,pdfname,pdfurl;

    public SharePdfModelClass() {
    }

    public SharePdfModelClass(String lawyeruid, String curentusername, String pdfname, String pdfurl) {
        this.lawyeruid = lawyeruid;
        this.curentusername = curentusername;
        this.pdfname = pdfname;
        this.pdfurl = pdfurl;
    }

    public String getLawyeruid() {
        return lawyeruid;
    }

    public void setLawyeruid(String lawyeruid) {
        this.lawyeruid = lawyeruid;
    }

    public String getCurentusername() {
        return curentusername;
    }

    public void setCurentusername(String curentusername) {
        this.curentusername = curentusername;
    }

    public String getPdfname() {
        return pdfname;
    }

    public void setPdfname(String pdfname) {
        this.pdfname = pdfname;
    }

    public String getPdfurl() {
        return pdfurl;
    }

    public void setPdfurl(String pdfurl) {
        this.pdfurl = pdfurl;
    }
}
