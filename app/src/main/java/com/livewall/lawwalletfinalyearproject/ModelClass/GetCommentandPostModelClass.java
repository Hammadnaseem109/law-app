package com.livewall.lawwalletfinalyearproject.ModelClass;

public class GetCommentandPostModelClass {
    String uid,username,userComment;

    public GetCommentandPostModelClass(String uid, String username, String userComment) {
        this.uid = uid;
        this.username = username;
        this.userComment = userComment;
    }

    public GetCommentandPostModelClass() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
