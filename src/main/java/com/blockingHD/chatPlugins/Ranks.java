package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.TreeMap;

/**
 * Created by MrKickkiller on 7/10/2015.
 */
public class Ranks extends ListenerAdapter<PircBotX> {
    static TreeMap<Integer,String> map = new TreeMap<>();
    static {
        map.put(1,"Rookie");
        map.put(100, "Average Joe");
        map.put(1000000, "Supreme");
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (event.getMessage().startsWith("!rank")){
            String username = event.getUser().getNick();
            username = username.replace("!rank","").trim().split(" ")[0];
            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(username)){
                int amountOfCookies = CookieBotMain.CDBM.getCookieAmountForPerson(username);
                boolean notFound = true;
                int tracker = 0;
                while (tracker <= map.keySet().toArray().length - 1 && amountOfCookies > (Integer) map.keySet().toArray()[tracker] ){
                    tracker ++;
                }
                if (tracker != 0){
                    event.getChannel().send().message(username + " is a " + map.get(map.keySet().toArray()[tracker-1]));
                }else {
                    event.getChannel().send().message(username + " is a " + map.get(1));
                }

            }
        }
    }
}
