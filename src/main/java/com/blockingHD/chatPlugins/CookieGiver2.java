package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.utils.JSONManipulator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MrKickkiller on 17/12/2015.
 */
public class CookieGiver2 {

    int timeBetweenCookieGiveAway = Integer.parseInt(CookieBotMain.prop.getProperty("timeBetweenCookieGiveaway"));
    int cookiesGivenOut = Integer.parseInt(CookieBotMain.prop.getProperty("cookiesGivenOut"));

    Timer checker = new Timer();
    Timer giver = new Timer();
    boolean giverOn;
    int counter;

    public CookieGiver2() {
        checker.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean live = JSONManipulator.isStreamLive("https://api.twitch.tv/kraken/streams/loneztar");
                if (live && !giverOn){
                    try {
                        giver.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                ArrayList<String> coll = JSONManipulator.getChatters("http://tmi.twitch.tv/group/user/"+ "loneztar" + "/chatters");
                                CookieBotMain.CDBM.addCookiesToAllCurrentViewers(coll, cookiesGivenOut);
                                if (coll != null){
                                    counter += coll.size();
                                }
                            }
                        }, 0 ,timeBetweenCookieGiveAway);

                    }catch (IllegalStateException e){

                    }
                    giverOn = true;
                } else if (!live && giverOn){
                    giver.cancel();
                    giverOn = false;
                }
            }
            // 3 secs ==> 30 secs
        },0, 30000);

    }
}
