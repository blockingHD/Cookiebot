package com.blockingHD.database;

/**
 * Created by MrKickkiller on 4/10/2015.
 */
public class StreamViewer {

    /*
    * The username of the viewer
     */
    private String name;

    /*
    * The amount of cookie's a person has
     */
    private int cookieCount;

    /*
    * The status of this person.
     */
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

    public void setCookieCount(int delta) {
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
