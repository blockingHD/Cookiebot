package com.blockingHD.commands;

import com.blockingHD.dao.CookieKeeper;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.regex.Pattern;

/**
 * Created by MrKickkiller on 1/11/2015.
 */
public class CookiesCommand implements Command {

    Pattern userMatcher = Pattern.compile("[^_][A-z0-9_]*");

    CookieKeeper ck = new CookieKeeper();

    @Override
    public void execute(MessageEvent event, String[] args) {
        if (args.length > 1 && userMatcher.matcher(args[1].trim().toLowerCase()).matches()){
            // Secondary name was given
            event.getChannel().send().message(args[1].trim() + " has " + ck.getAmountOfCookies(args[1].trim()) + " cookies in their secret stash!");
        }else {
            // No secondary name was given.
            event.getChannel().send().message(event.getUser().getNick().trim() + " has " + ck.getAmountOfCookies(event.getUser().getNick().trim()) + " cookies in their secret stash");
        }
    }
}
