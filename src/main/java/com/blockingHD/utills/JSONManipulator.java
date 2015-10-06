package com.blockingHD.utills;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by blockingHD on 06/10/2015.
 */
public class JSONManipulator {

    public static void getChatters(String url){

        try {
            Gson gson = new Gson();
            TwitchApiReturn returnVal = gson.fromJson(new BufferedReader(new InputStreamReader(new URL(url).openStream())), TwitchApiReturn.class);
            System.out.println(returnVal.getChatter_count());
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
