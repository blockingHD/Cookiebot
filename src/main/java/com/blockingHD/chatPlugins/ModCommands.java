package com.blockingHD.chatPlugins;

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
        if (CDBM.isPersonAlreadyInDatabase(username.trim()) && CDBM.getModStatusForPerson(username.trim()) && message.trim().contains("!mod")) {
            if (message.startsWith("!mod")) {
                String target = message.replace("!mod", "").trim();
                if (CDBM.isPersonAlreadyInDatabase(target)) {
                    CDBM.updateModStatus(target, true);
                    event.getChannel().send().message(username + " has modded " + target);
                }
            }

            if (message.startsWith("!unmod")) {
                String target = message.replace("!unmod", "").trim();
                if (CDBM.isPersonAlreadyInDatabase(target) && !username.equals(target)){
                    CDBM.updateModStatus(target, false);
                    event.getChannel().send().message(username + " has unmodded " + target);
                }else if (CDBM.isPersonAlreadyInDatabase(target)) {
                    event.getChannel().send().message("Why would you take mod away from yourself!");
                }
            }
        }else if ((message.trim().startsWith("!mod") || message.trim().startsWith("!unmod") && CDBM.isPersonAlreadyInDatabase(username.trim()) && !CDBM.getModStatusForPerson(username.trim()))){
            event.getChannel().send().message("This command requires mod permission.");
        }
    }
}