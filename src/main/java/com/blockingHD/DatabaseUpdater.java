package com.blockingHD;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by MrKickkiller on 5/10/2015.
 */
public class DatabaseUpdater extends ListenerAdapter<PircBotX> {
    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        // Add all users to the database.
        if (! CookieBotMain.CDBM.isPersonAlreadyInDatabase(event.getUser().getNick().trim())){

            CookieBotMain.CDBM.initPersonInDatabase(event.getUser().getNick().trim());
        }
        super.onMessage(event);
    }

}
