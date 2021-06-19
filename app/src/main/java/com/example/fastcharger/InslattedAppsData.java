package com.example.fastcharger;

import android.graphics.drawable.Drawable;

public class InslattedAppsData {

    private String name;
    Drawable icon;
    private String packages;
    private boolean isActive = true;
    public InslattedAppsData(String name, Drawable icon, String packages) {
        this.name = name;
        this.icon = icon;
        this.packages = packages;
    }
    public String getName() {
        return name;
    }
    public Drawable getIcon() {
        return icon;
    }
    public String getPackages() {
        return packages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
