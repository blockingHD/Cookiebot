package com.blockingHD.utills;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by blockingHD on 06/10/2015.
 */
public class JSONManipulator {

    public static ArrayList<String> getChatters(String url){
        try {
            Gson gson = new Gson();
            TwitchApiReturn returnVal = gson.fromJson(new BufferedReader(new InputStreamReader(new URL(url).openStream())), TwitchApiReturn.class);
            return returnVal.getChatters().getAllViewersInChat();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getModerators(String url){
        try {
            Gson gson = new Gson();
            TwitchApiReturn returnVal = gson.fromJson(new BufferedReader(new InputStreamReader(new URL(url).openStream())), TwitchApiReturn.class);
            return returnVal.getChatters().getModerators();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
