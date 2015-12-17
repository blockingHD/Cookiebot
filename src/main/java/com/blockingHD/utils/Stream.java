package com.blockingHD.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mathieu on 17/12/2015.
 */
public class Stream {

    @SerializedName("_id")
    public String _id;

    public String getValue() {
        return _id;
    }

    public void setValue(String value) {
        this._id = value;
    }
}
