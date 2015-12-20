package com.blockingHD.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by blockingHD on 06/10/2015.
 */
public class JSONManipulator {

    public static ArrayList<String> getChatters(String url){
        InputStream is = null;
        try {
            Gson gson = new Gson();
            URL u = new URL(url);
            is = u.openStream();
            TwitchApiReturn returnVal = gson.fromJson(new BufferedReader(new InputStreamReader(is)), TwitchApiReturn.class);
            return returnVal.getChatters().getAllViewersInChat();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static ArrayList<String> getModerators(String url){
        InputStream is = null;
        try {
            Gson gson = new Gson();
            URL u = new URL(url);
            is = u.openStream();
            TwitchApiReturn returnVal = gson.fromJson(new BufferedReader(new InputStreamReader(is)), TwitchApiReturn.class);
            return returnVal.getChatters().getModerators();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean isStreamLive (String url){
        InputStream inputStream = null;
        try {
            Gson gson = new Gson();
            URL urlU = new URL(url);
            inputStream = urlU.openStream();
            KrakenStreamReturn streamReturn = gson.fromJson(new BufferedReader(new InputStreamReader(inputStream)), KrakenStreamReturn.class);
            return streamReturn.stream != null || streamReturn.getStream() != null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
