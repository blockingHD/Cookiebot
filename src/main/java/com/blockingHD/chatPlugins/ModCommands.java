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
        String message = event.getMessage().toLowerCase();
        if ( CDBM.isPersonAlreadyInDatabase(username.trim())&&CDBM.getModStatusForPerson(username.trim())) {
            if (message.contains("!mod")) {
                String target = message.replace("!mod", "").trim();
                if (CDBM.isPersonAlreadyInDatabase(target)) {
                    CDBM.updateModStatus(target, true);
                    event.getChannel().send().message(username + " has modded " + target);
                }
            }

            if (message.contains("!unmod")) {
                String target = message.replace("!unmod", "").trim();
                if (CDBM.isPersonAlreadyInDatabase(target) && !username.equals(target)){
                    CDBM.updateModStatus(target, false);
                    event.getChannel().send().message(username + " has unmodded " + target);
                }
            }

            if (message.contains("!takecookies")){
                String[] command = message.split(" ");

                if (command.length == 3 && isInt(command[2])){
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

    private boolean isInt(String string){
        try {
            Integer.parseInt(string.trim());
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}