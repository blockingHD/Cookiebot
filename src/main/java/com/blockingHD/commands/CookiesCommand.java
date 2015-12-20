package com.blockingHD.commands;

import com.blockingHD.CookieBotMain;
import com.blockingHD.dao.CookieKeeper;
import com.blockingHD.exceptions.OutOfCookieException;
import org.apache.commons.lang3.text.WordUtils;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by MrKickkiller on 1/11/2015.
 */
public class CookiesCommand implements Command {

    Pattern userMatcher = Pattern.compile("[^_][A-z0-9_]*");

    HashMap<String, Command> subCommandHashMap = new HashMap<>();

    CookieKeeper ck = new CookieKeeper();

    public CookiesCommand() {
        subCommandHashMap.put("give", new CookiesGiveSubCommand());
        subCommandHashMap.put("take", new CookiesTakeSubCommand());
        subCommandHashMap.put("add", new CookiesAddSubCommand());
    }

    public void execute(MessageEvent event, String[] args) throws Exception {
        if (args.length > 1){
            if (subCommandHashMap.containsKey(args[1]) && args.length == 2){
                event.respond("Invalid Cookie-Command. Usage: !cookies < | give | take | add> <Username> <Amount >= 0>");
            }
            if (subCommandHashMap.containsKey(args[1]) && args.length > 2){
                subCommandHashMap.get(args[1].toLowerCase()).execute(event,args);
            }
            else if (userMatcher.matcher(args[1].trim().toLowerCase()).matches()){
                // Secondary name was given
                String amount = ck.getAmountOfCookies(args[1].trim().toLowerCase());
                event.getChannel().send().message(args[1].trim() + " has " + amount + " cookies in their secret stash!");
            }
        }
        else {
            // No secondary name was given.
            event.getChannel().send().message(event.getUser().getNick().trim() + " has " + ck.getAmountOfCookies(event.getUser().getNick().trim()) + " cookies in their secret stash");
        }
    }

    class CookiesGiveSubCommand implements Command{

        @Override
        public void execute(MessageEvent event, String[] args) throws OutOfCookieException {
            String sender = CookieBotMain.CDBM.isPersonAlreadyInDatabase(event.getUser().getNick().toLowerCase().trim()) ? event.getUser().getNick().toLowerCase().trim() : "";
            String receiver = CookieBotMain.CDBM.isPersonAlreadyInDatabase(args[2].toLowerCase().trim()) ? args[2].toLowerCase().trim() : "";
            int amount = Integer.valueOf(args[3]);
            if (sender != "" && receiver != "" && amount >= 0){
                if (CookieBotMain.CDBM.getCookieAmountForPerson(sender) >= amount){
                    CookieBotMain.CDBM.takeCookiesFromUser(sender,amount);
                    CookieBotMain.CDBM.addCookiesToUser(receiver,amount);
                    event.getChannel().send().message(WordUtils.capitalize(sender) + " has gifted " + amount + " cookies to " + WordUtils.capitalize(receiver));
                }
            }
        }

    }

    class CookiesTakeSubCommand implements Command{

        @Override
        public void execute(MessageEvent event, String[] args) throws OutOfCookieException {
            String sender = CookieBotMain.CDBM.isPersonAlreadyInDatabase(event.getUser().getNick().toLowerCase().trim()) ? event.getUser().getNick().toLowerCase().trim() : "";
            String receiver = CookieBotMain.CDBM.isPersonAlreadyInDatabase(args[2].toLowerCase().trim()) ? args[2].toLowerCase().trim() : "";
            int amount = Integer.valueOf(args[3]);
            if (sender != "" && receiver != "" && amount >= 0){
                if (CookieBotMain.CDBM.getModStatusForPerson(sender)){
                    CookieBotMain.CDBM.takeCookiesFromUser(receiver, amount);
                }
            }
        }
    }

    class CookiesAddSubCommand implements Command{

        @Override
        public void execute(MessageEvent event, String[] args) {
            String sender = CookieBotMain.CDBM.isPersonAlreadyInDatabase(event.getUser().getNick().toLowerCase().trim()) ? event.getUser().getNick().toLowerCase().trim() : "";
            String receiver = CookieBotMain.CDBM.isPersonAlreadyInDatabase(args[2].toLowerCase().trim()) ? args[2].toLowerCase().trim() : "";
            int amount = Integer.valueOf(args[3]);
            if (sender != "" && receiver != "" && amount >= 0){
                if (CookieBotMain.CDBM.getModStatusForPerson(sender)){
                    CookieBotMain.CDBM.addCookiesToUser(receiver, amount);
                }
            }
        }
    }


}
