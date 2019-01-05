package com.example.taruc.instacity;

public class ProfileClass {
    public String caption;
    public String date;
    private String postImage;
    public String time;

    public ProfileClass(String caption, String date, String postImage, String time) {
        this.caption = caption;
        this.date = date;
        this.postImage = postImage;
        this.time = time;
    }

    public ProfileClass() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
