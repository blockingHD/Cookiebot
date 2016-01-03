package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.Referance;
import com.blockingHD.utils.CookieTimer;
import com.blockingHD.utils.JSONManipulator;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by MrKickkiller on 17/12/2015.
 */
public class CookieGiver2 {

    int timeBetweenCookieGiveAway = Integer.parseInt(CookieBotMain.prop.getProperty("timeBetweenCookieGiveaway"));
    int cookiesGivenOut = Integer.parseInt(CookieBotMain.prop.getProperty("cookiesGivenOut"));
    String channelName = Referance.CHAN;

    CookieTimer checker = new CookieTimer();
    CookieTimer giver = new CookieTimer();
    int counter;

    public CookieGiver2() {
        setTimer();
    }

    private void setTimer(){
        checker.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                boolean live = JSONManipulator.isStreamLive("https://api.twitch.tv/kraken/streams/" + channelName);
                System.out.println("Live: "+ live + " TimerStatus of giver: " + giver.isOn());
                if (live && !giver.isOn()){
                    try {
                        giver.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                ArrayList<String> coll = JSONManipulator.getChatters("http://tmi.twitch.tv/group/user/"+ channelName + "/chatters");
                                CookieBotMain.CDBM.addCookiesToAllCurrentViewers(coll, cookiesGivenOut);
                                if (coll != null){
                                    counter += coll.size();
                                }
                            }
                        }, 0 ,timeBetweenCookieGiveAway);

                    }catch (Exception e){
                        e.printStackTrace();
                        // Because suspicion that whenever something goes wrong the timer stops.
                        checker.purge();
                        giver.purge();
                        giver.setOn(false);
                        setTimer();
                    }
                    giver.setOn(true);
                } else if (!live && giver.isOn()){
                    giver.purge();
                    giver.setOn(false);
                }
            }
            // 3 secs ==> 30 secs
        },0, 30000);
    }
}
