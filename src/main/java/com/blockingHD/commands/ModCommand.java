package com.blockingHD.commands;

import com.blockingHD.CookieBotMain;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by Mathieu Coussens on 3/11/2015
 * Behoort tot Cookiebot
 * Created in IntelliJ
 */
public class ModCommand implements Command {

    @Override
    public void execute(MessageEvent event, String[] args) throws Exception {
        if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(event.getUser().getNick().toLowerCase().trim()) && CookieBotMain.CDBM.isPersonAlreadyInDatabase(args[1]) && CookieBotMain.CDBM.getModStatusForPerson(event.getUser().getNick().toLowerCase().trim())){
            CookieBotMain.CDBM.updateModStatus(args[1],true);
        }
    }
}
