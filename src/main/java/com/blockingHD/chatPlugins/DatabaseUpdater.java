package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.utills.JSONManipulator;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by MrKickkiller on 5/10/2015.
 */
public class DatabaseUpdater extends ListenerAdapter<PircBotX>{

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String urlPart = event.getChannel().getName().replace("#","").toLowerCase().trim();
        for (String s : JSONManipulator.getChatters("http://tmi.twitch.tv/group/user/"+ urlPart+ "/chatters")){
            if (!CookieBotMain.CDBM.isPersonAlreadyInDatabase(s)){
                CookieBotMain.CDBM.initPersonInDatabase(s);
            }
        }
        for (String p: JSONManipulator.getModerators("http://tmi.twitch.tv/group/user/"+ urlPart+ "/chatters")){
            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(p)){
                CookieBotMain.CDBM.updateModStatus(p, true);
            }
        }
    }
}
