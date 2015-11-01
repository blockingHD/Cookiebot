package com.blockingHD.dao;

import com.blockingHD.CookieBotMain;
import com.blockingHD.database.CookieDataBaseManipulator;

/**
 * Created by MrKickkiller on 1/11/2015.
 */
public class CookieKeeper {

    private CookieDataBaseManipulator db = CookieBotMain.CDBM;

    public int getAmountOfCookies(String username){
        return (db.isPersonAlreadyInDatabase(username)) ? db.getCookieAmountForPerson(username) : null;
    }
}
