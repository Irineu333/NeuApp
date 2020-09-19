package com.neuapp.neu.Models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class Repositorio {

    private String name;
    private int id;
    private String login;
    private String avatarUrl;
    private String url;

    public int getStars() {
        return stars;
    }

    private int stars;

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public Repositorio(String name, String login, int id, String url) {
        this.name = name;
        this.id = id;
        this.login = login;
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + getName() + "Id: " + getId();
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setDescription(String description) {

    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setAvatarBitmap(Bitmap avatar) {
    }
}
