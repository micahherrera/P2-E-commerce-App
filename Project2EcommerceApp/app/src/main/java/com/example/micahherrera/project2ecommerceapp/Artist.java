package com.example.micahherrera.project2ecommerceapp;

/**
 * Created by micahherrera on 6/27/16.
 */
public class Artist {
    private String name;
    private String bio;
    private String imageLink;

    public Artist(String name, String bio, String imageLink) {
        this.name = name;
        this.bio = bio;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
