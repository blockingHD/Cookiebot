package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.database.StreamViewer;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by blockingHD on 03/10/2015.
 */
public class UserCommands extends ListenerAdapter<PircBotX> {
    
    //To Do: add commands and returns to props file
    
    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if(event.getMessage().equalsIgnoreCase("!hello")) {
            event.getChannel().send().message("Hi how are you today?");
        }else if(event.getMessage().startsWith("!cookies give")){
            // !cookies give username amount
            String receiverAndCount = event.getMessage().replace("!cookies give","").trim();
            String receiver = receiverAndCount.split(" ")[0];
            int amount = Integer.parseInt(receiverAndCount.split(" ")[1]);
            String sender = event.getUser().getNick().toLowerCase().trim();

            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(sender.trim()) && CookieBotMain.CDBM.takeCookiesFromUser(sender.trim(), amount)){
                CookieBotMain.CDBM.addCookiesToUser(receiver.trim(),amount);
                event.getChannel().send().message( sender + " has given " + receiver + " " + amount+ " of cookies");
            }else {
                event.getChannel().send().message("Transaction failed. " + sender + " : Make sure you have enough cookies");
            }

        }else if (event.getMessage().startsWith("!cookies")){

            /*
            * Retrieves the cookies from a person and displays them.
            *
            * Uses:
            *  - !cookies = Display the user's cookies, that uses the command
            *  - !cookies <Username> = Display the cookies of Username
            *  - !cookies <Username> <Additional text for a ping> = Same as !cookies <Username>, but allows for more text
            *                                                       to be added.
            *
            */

            String username = event.getMessage().replace("!cookies","");
            username = username.trim(); // Trim all leading and following spaces, because they are nasty buggers.
            int indexOfSpace = username.indexOf(' ');
            if (indexOfSpace != -1){
                username = username.substring(0,indexOfSpace);
            }
            if (username.length() == 0){
                username = event.getUser().getNick();
            }
            int amount = CookieBotMain.CDBM.getCookieAmountForPerson(username);
            event.getChannel().send().message(username + " has " + amount + " cookies in their secret stash");
        }
    }
}
