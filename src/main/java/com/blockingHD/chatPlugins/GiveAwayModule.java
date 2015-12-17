package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.exceptions.OutOfCookieException;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by MrKickkiller on 17/12/2015.
 */
public class GiveAwayModule {

    ArrayList<String> entries = new ArrayList<>();

    int price;

    public boolean userEnters(String username) throws OutOfCookieException {
        if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(username) && ! entries.contains(username) &&CookieBotMain.CDBM.getCookieAmountForPerson(username) >= price){
            CookieBotMain.CDBM.takeCookiesFromUser(username,price);
            entries.add(username);
            return true;
        }
        return false;
    }

    public void restart(){
        entries.clear();
    }

    public String randomlyDraw(){
        System.out.println(entries.size());
        if (entries.size() > 1)
            return entries.get(new Random().nextInt(entries.size()-1));
        else if (entries.size() != 0);
            return entries.get(0);
    }

    public void setPrice(int price){
        this.price = price;
    }


}
