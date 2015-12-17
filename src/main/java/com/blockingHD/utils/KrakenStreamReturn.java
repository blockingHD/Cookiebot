package com.blockingHD.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mathieu on 17/12/2015.
 */
public class KrakenStreamReturn {
    @SerializedName("stream")
    Stream stream;

    public String getStream() {
        if (stream == null) {
            return null;
        }
        else return stream.getValue();
    }

    public void setStream(String streamH) {
        stream = new Stream();
        stream.setValue(streamH);
    }
}
