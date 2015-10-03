package com.blockingHD.commands;

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
        }
    }
}
