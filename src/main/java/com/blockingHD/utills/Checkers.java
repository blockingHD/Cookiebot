package com.blockingHD.utills;

/**
 * Created by blockingHD on 07/10/2015.
 */
public class Checkers {

    public boolean isInt(String string){
        try {
            Integer.parseInt(string.trim());
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}
