package com.blockingHD.commands;

import com.blockingHD.CookieBotMain;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Mathieu Coussens on 3/11/2015
 * Behoort tot Cookiebot
 * Created in IntelliJ
 */
public class CommandsCommand implements Command {
    @Override
    public void execute(MessageEvent event, String[] args) throws Exception {
        String url = (String) CookieBotMain.prop.get("commandListURL");
        event.respond("Command list can be found at: " + url);
    }
}
