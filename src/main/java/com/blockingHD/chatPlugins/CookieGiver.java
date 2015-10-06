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
                        ImmutableCollection coll = event.getChannel().getUsers();
                        ArrayList<String> usersInChat = new ArrayList<String>(coll);
                        CookieBotMain.CDBM.addOneCookieToAllCurrentViewers(usersInChat);
                        counter += usersInChat.size();
                    }
            },0,3000);
        }else if (message.toLowerCase().startsWith("!stopstream") &&
                CookieBotMain.CDBM.isPersonAlreadyInDatabase(caller.getNick().toLowerCase().trim()) &&
                CookieBotMain.CDBM.getModStatusForPerson(caller.getNick().toLowerCase().trim())){
            event.getChannel().send().message(Integer.toString(counter) + " cookies have been given away this stream");
            counter = 0;
            t.cancel();
        }
    }
}
