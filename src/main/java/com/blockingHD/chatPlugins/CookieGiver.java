package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.google.common.collect.ImmutableCollection;
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

    int timeBetweenCookieGiveAway = Integer.parseInt(CookieBotMain.prop.getProperty("timeBetweenCookieGiveaway"));
    int cookiesGivenOut = Integer.parseInt(CookieBotMain.prop.getProperty("cookiesGivenOut"));

    Timer t = new Timer();
    int counter = 0;

    @Override
    public void onMessage(final MessageEvent<PircBotX> event) throws Exception {
        String message = event.getMessage();
        User caller = event.getUser();
        if (message.toLowerCase().startsWith("!startstream") &&
                CookieBotMain.CDBM.isPersonAlreadyInDatabase(caller.getNick().toLowerCase().trim()) &&
                CookieBotMain.CDBM.getModStatusForPerson(caller.getNick().toLowerCase().trim())){
            t.scheduleAtFixedRate(
                    new TimerTask() {
                    @Override
                    public void run() {
                        ImmutableCollection<User> coll = event.getChannel().getNormalUsers();
                        ArrayList<String> usersInChat = new ArrayList<String>();
                        for (User user: coll){
                            usersInChat.add(user.getNick());
                        }
                        CookieBotMain.CDBM.addCookiesToAllCurrentViewers(usersInChat, cookiesGivenOut);
                        counter += usersInChat.size();
                    }
            },0,timeBetweenCookieGiveAway);
        }else if (message.toLowerCase().startsWith("!stopstream") &&
                CookieBotMain.CDBM.isPersonAlreadyInDatabase(caller.getNick().toLowerCase().trim()) &&
                CookieBotMain.CDBM.getModStatusForPerson(caller.getNick().toLowerCase().trim())){
            event.getChannel().send().message(Integer.toString(counter) + " cookies have been given away this stream");
            counter = 0;
            t.cancel();

        }else if (message.toLowerCase().trim().startsWith("!cookiesthisstream")){
            event.getChannel().send().message(counter + " cookies have been given away already this stream!");
        }
    }
}
