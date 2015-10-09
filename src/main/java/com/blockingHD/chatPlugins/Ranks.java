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
    //TODO: needs a prop option.
    static {
        map.put(1,"Rookie");
        map.put(100, "Average Joe");
        map.put(1000000, "Supreme");
    }

    boolean enabled = Boolean.parseBoolean(CookieBotMain.prop.getProperty("ranksEnabled"));

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (!enabled){
            return;
        }

        //Shows someones rank based for there cookie count.
        if (event.getMessage().startsWith("!rank")){
            String username = event.getUser().getNick();
            username = username.replace("!rank","").trim().split(" ")[0];
            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(username)){
                int amountOfCookies = CookieBotMain.CDBM.getCookieAmountForPerson(username);
                int tracker = 0;
                while (tracker <= map.keySet().toArray().length - 1 && amountOfCookies > (Integer) map.keySet().toArray()[tracker] ){
                    tracker ++;
                }
                if (username.equals("blockinghd") || username.equals("mrkickkiller")) {
                    event.getChannel().send().message(username + " is my creator!");
                }else if (username.equals("loneztar")) {
                    event.getChannel().send().message(username + " is GOD!");
                }else if (tracker != 0) {
                    event.getChannel().send().message(username + " is a " + map.get(map.keySet().toArray()[tracker - 1]));
                } else {
                    event.getChannel().send().message(username + " is a " + map.get(1));
                }
            }
        }
    }
}

