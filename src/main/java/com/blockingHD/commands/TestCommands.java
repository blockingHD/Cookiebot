package com.blockingHD.commands;

import com.blockingHD.database.CookieDataBaseManipulator;
import com.blockingHD.database.CookieDatabase;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by blockingHD on 03/10/2015.
 */
public class TestCommands extends ListenerAdapter<PircBotX> {
    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if(event.getMessage().equalsIgnoreCase("!hello")) {
            event.getChannel().send().message("Hi how are you today?");
        }else if (event.getMessage().startsWith("!cookies")){
            String username = event.getMessage().replace("!cookies","");
            CookieDataBaseManipulator manipulator = new CookieDataBaseManipulator(new CookieDatabase());
            int amount = manipulator.getCookieAmountForPerson(username);
            event.getChannel().send().message(username + " has " + amount + " cookies in his secret stash");
        }
    }
}
