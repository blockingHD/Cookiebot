package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import static com.blockingHD.CookieBotMain.CDBM;
import static com.blockingHD.CookieBotMain.CHECKERS;

/**
 * Created by blockingHD on 05/10/2015.
 */
public class ModCommands extends ListenerAdapter<PircBotX> {
    boolean enabled = Boolean.parseBoolean(CookieBotMain.prop.getProperty("modCommandsEnabled"));

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (!enabled){
            return;
        }
        String username = event.getUser().getNick();
        String message = event.getMessage().toLowerCase();
        if ( CDBM.isPersonAlreadyInDatabase(username.trim())&&CDBM.getModStatusForPerson(username.trim())) {
            //Sets the mod boolean in the data base to true for selected person.
            if (message.contains("!mod")) {
                String target = message.replace("!mod", "").trim();
                if (CDBM.isPersonAlreadyInDatabase(target)) {
                    CDBM.updateModStatus(target, true);
                    event.getChannel().send().message(username + " has modded " + target);
                }
            }
            //Sets the mod boolean in the data base to false for selected person.
            if (message.contains("!unmod")) {
                String target = message.replace("!unmod", "").trim();
                if (CDBM.isPersonAlreadyInDatabase(target) && !username.equals(target)){
                    CDBM.updateModStatus(target, false);
                    event.getChannel().send().message(username + " has unmodded " + target);
                }
            }
            //Takes a specified amount of cookies from a specified user.
            if (message.contains("!takecookies")){
                String[] command = message.split(" ");
                if (command.length == 3 && CHECKERS.isInt(command[2])){
                    if (CDBM.isPersonAlreadyInDatabase(command[1]) && CDBM.getCookieAmountForPerson(command[1]) >= Integer.parseInt(command[2])){
                        try {
                            CDBM.takeCookiesFromUser(command[1], Integer.parseInt(command[2]));
                        }catch (NumberFormatException e){
                            throw e;
                        }
                    }else if (CDBM.isPersonAlreadyInDatabase(command[1]) && CDBM.getCookieAmountForPerson(command[1]) < Integer.parseInt(command[2])){
                        CDBM.takeCookiesFromUser(command[1], CDBM.getCookieAmountForPerson(command[1]));
                    }
                }
            }
            //Gives a specified amount of cookies to a specified user.
            if (message.contains("!givecookies")){
                String[] command = message.split(" ");
                if (command.length == 3){
                    if (CDBM.isPersonAlreadyInDatabase(command[1])){
                        try {
                            CDBM.addCookiesToUser(command[1], Integer.parseInt(command[2]));
                        }catch (NumberFormatException e){
                            throw e;
                        }
                    }
                }
            }
        }
    }

}