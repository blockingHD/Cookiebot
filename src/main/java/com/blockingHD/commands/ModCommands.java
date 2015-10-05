package com.blockingHD.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import static com.blockingHD.CookieBotMain.CDBM;

/**
 * Created by blockingHD on 05/10/2015.
 */
public class ModCommands extends ListenerAdapter<PircBotX> {

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String username = event.getUser().getNick();
        String message = event.getMessage();
        if (CDBM.getModStatusForPerson(username)) {
            if (message.contains("!mod")) {
                String target = message.replace("!mod", "").trim();
                if (CDBM.isPersonAlreadyInDatabase(target)) {
                    CDBM.updateModStatus(target, true);
                    event.getChannel().send().message(username + " has moded " + target);
                }
            }

            if (message.contains("!unmod")) {
                String target = message.replace("!unmod", "").trim();
                if (CDBM.isPersonAlreadyInDatabase(target) && !username.equals(target)){
                    CDBM.updateModStatus(target, false);
                    event.getChannel().send().message(username + " has unmoded " + target);
                }
            }
        }
    }
}