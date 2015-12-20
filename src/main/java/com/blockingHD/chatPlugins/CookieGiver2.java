package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.utils.CookieTimer;
import com.blockingHD.utils.JSONManipulator;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by MrKickkiller on 17/12/2015.
 */
public class CookieGiver2 {

    int timeBetweenCookieGiveAway = Integer.parseInt(CookieBotMain.prop.getProperty("timeBetweenCookieGiveaway"));
    int cookiesGivenOut = Integer.parseInt(CookieBotMain.prop.getProperty("cookiesGivenOut"));

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
                boolean live = JSONManipulator.isStreamLive("https://api.twitch.tv/kraken/streams/loneztar");
                System.out.println("Live: "+ live + " TimerStatus of giver: " + giver.isOn());
                if (live && !giver.isOn()){
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

    // Executor that checks if stream is live
    ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
    // Executor that hands out the cookies
    ScheduledExecutorService cex = Executors.newSingleThreadScheduledExecutor();
    // Task that gives out the cookies
    Future giver2;

    public void jezzaShedule(){
        // Every 30 seconds check if stream is live.
        ex.scheduleAtFixedRate(() -> {
            // Returns if stream is live.
            boolean live = JSONManipulator.isStreamLive("https://api.twitch.tv/kraken/streams/loneztar");
            // Giver2 (cookie) is no longer working && Stream is live
            if (live && giver2 != null && (giver2 == null ||giver2.isDone() || giver2.isCancelled())){
                giver2 =  cex.scheduleAtFixedRate(() -> {
                    ArrayList<String> coll = JSONManipulator.getChatters("http://tmi.twitch.tv/group/user/"+ "loneztar" + "/chatters");
                    CookieBotMain.CDBM.addCookiesToAllCurrentViewers(coll, cookiesGivenOut);
                    if (coll != null){
                        counter += coll.size();
                    }
                }, 0 , timeBetweenCookieGiveAway, TimeUnit.MILLISECONDS);
            }
            // Stream goes off and work is not finished or cancelled
            else if (!live && !(giver2 == null || giver2.isDone() || giver2.isCancelled())){
                giver2.cancel(true);
            }
        }, 0, 30 , TimeUnit.SECONDS);
    }


}
