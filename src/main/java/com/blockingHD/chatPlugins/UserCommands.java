package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.utills.JSONMonipulator;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.net.URL;

/**
 * Created by blockingHD on 03/10/2015.
 */
public class UserCommands extends ListenerAdapter<PircBotX> {
    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if(event.getMessage().equalsIgnoreCase("!hello")) {
            event.getChannel().send().message("Hi how are you today?");
            //Debugging command!
            JSONMonipulator.getChatters(new URL("http://tmi.twitch.tv/group/user/blockinghd/chatters"));
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
