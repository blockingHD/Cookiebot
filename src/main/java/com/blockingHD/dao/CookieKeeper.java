package com.blockingHD.dao;

import com.blockingHD.CookieBotMain;
import com.blockingHD.database.CookieDataBaseManipulator;

/**
 * Created by MrKickkiller on 1/11/2015.
 */
public class CookieKeeper {

    private CookieDataBaseManipulator db = CookieBotMain.CDBM;

    public String getAmountOfCookies(String username){
        if (!db.isPersonAlreadyInDatabase(username)){
            return "an unknown number of";
        }
        return String.valueOf(db.getCookieAmountForPerson(username));
    }
}
