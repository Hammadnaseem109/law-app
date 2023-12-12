package com.livewall.lawwalletfinalyearproject.ModelClass;

public class CommentModelClass {
    String userID,postkey,username,profileimage,comment,postimage;

    public CommentModelClass() {
    }

    public CommentModelClass(String userID, String postkey, String username, String profileimage, String comment, String postimage) {
        this.userID = userID;
        this.postkey = postkey;
        this.username = username;
        this.profileimage = profileimage;
        this.comment = comment;
        this.postimage = postimage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostkey() {
        return postkey;
    }

    public void setPostkey(String postkey) {
        this.postkey = postkey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }
}
