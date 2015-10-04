package com.blockingHD.database;

import com.sun.istack.internal.NotNull;

/**
 * Created by Mathieu on 4/10/2015.
 */
public class StreamViewer {

    private String name;

    private int cookieCount = 0;

    private boolean modStatus;

    public StreamViewer(String name, boolean modStatus, int cookieCount) {
        this.name = name;
        this.modStatus = modStatus;
        this.cookieCount = cookieCount;
    }

    public StreamViewer(String name){
        this(name, false, 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCookieCount() {
        return cookieCount;
    }

    public void setCookieCount(@NotNull int delta) {
        if (delta > 0){
            this.cookieCount += delta;
        }
    }

    public boolean isModStatus() {
        return modStatus;
    }

    public void setModStatus(boolean modStatus) {
        this.modStatus = modStatus;
    }
}
