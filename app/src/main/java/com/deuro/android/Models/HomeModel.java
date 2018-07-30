package com.deuro.android.Models;

import android.graphics.drawable.Drawable;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeModel {
    public String title;
    public String decription;
    public String image;
    public int drawable;
    public int position;

    public HomeModel(String title, String decription, int drawable) {
        this.title = title;
        this.decription = decription;
        this.drawable = drawable;
    }

    public HomeModel(JSONObject jsonObject) {
        try {
            setTitle(jsonObject.getString("title"));
            setDecription(jsonObject.getString("subtitle"));
            setImage(jsonObject.getString("image"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String photo) {
        this.image = photo;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
