package com.example.mueveteunac2.viewUser.data.model;

public class Profile {

    private String userName;
    private String userEmail;
    private String userImage;

    public Profile() {
        // Constructor sin argumentos
    }

    public Profile(String userName, String userEmail, String userImage) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userImage = userImage;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
