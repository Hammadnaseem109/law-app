package com.livewall.lawwalletfinalyearproject.ModelClass;

public class BookAppiontmentModelClass {
  String id, name ,phonenumber,message;
public BookAppiontmentModelClass(){

}
    public BookAppiontmentModelClass(String id, String name, String phonenumber, String message) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
