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
        if (CDBM.getModStatusForPerson(username)){
            if (message.equalsIgnoreCase("!mod")){
                String target = message.replace("!mod", "").trim();
                CDBM.updateModStatus(username, true);
                event.getChannel().send().message(".w blockinghd " + username + " has moded" + target);
            }
        }
    }
}
