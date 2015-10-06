package com.blockingHD.utills;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by blockingHD on 06/10/2015.
 */
public class JSONMonipulator {

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url1 = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url1.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static void getChatters(String url){

        try {

        }catch (Exception e){
            System.out.println(e);
        }
    }
}
