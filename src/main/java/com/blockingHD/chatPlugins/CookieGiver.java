package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.utills.JSONManipulator;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MrKickkiller on 6/10/2015
 */
public class CookieGiver extends ListenerAdapter<PircBotX> {

    Timer t = new Timer();
    int counter = 0;

    int timeBetweenCookieGiveAway = Integer.parseInt(CookieBotMain.prop.getProperty("timeBetweenCookieGiveaway"));
    int cookiesGivenOut = Integer.parseInt(CookieBotMain.prop.getProperty("cookiesGivenOut"));
    boolean enabled = Boolean.parseBoolean(CookieBotMain.prop.getProperty("cookieGiverEnabled"));


    @Override
    public void onMessage(final MessageEvent<PircBotX> event) throws Exception {
        if (! enabled){
            return;
        }
        String message = event.getMessage();
        User caller = event.getUser();

        //Starts giving people cookies with specified fields found in the props file.
        if (message.toLowerCase().startsWith("!startstream") &&
                CookieBotMain.CDBM.isPersonAlreadyInDatabase(caller.getNick().toLowerCase().trim()) &&
                CookieBotMain.CDBM.getModStatusForPerson(caller.getNick().toLowerCase().trim())){
            final String urlPart = event.getChannel().getName().replace("#","").trim().toLowerCase();
            t.scheduleAtFixedRate(
                    new TimerTask() {
                    @Override
                    public void run() {
                        ArrayList<String> coll = JSONManipulator.getChatters("http://tmi.twitch.tv/group/user/"+ urlPart + "/chatters");
                        CookieBotMain.CDBM.addCookiesToAllCurrentViewers(coll, cookiesGivenOut);
                        if (coll != null){
                            counter += coll.size();
                        }
                    }
            },0,timeBetweenCookieGiveAway);
        //Stops giving people cookies and tells everyone how many cookies have been given out that stream.
        }else if (message.toLowerCase().startsWith("!stopstream") &&
                CookieBotMain.CDBM.isPersonAlreadyInDatabase(caller.getNick().toLowerCase().trim()) &&
                CookieBotMain.CDBM.getModStatusForPerson(caller.getNick().toLowerCase().trim())){
            event.getChannel().send().message(Integer.toString(counter) + " cookies have been given away this stream");
            counter = 0;
            t.cancel();
        //Tells everyone how many cookies have been given out.
        }else if (message.toLowerCase().trim().startsWith("!cookiesthisstream")){
            event.getChannel().send().message(counter + " cookies have been given away already this stream!");
        }
    }
}
