package com.blockingHD.games;

import com.blockingHD.CookieBotMain;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by MrKickkiller on 13/10/2015
 */
public class Bidding extends ListenerAdapter<PircBotX> {

    String currentTopUser;
    int currentBid;
    boolean biddingStarted;

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String username = event.getUser().getNick().trim().toLowerCase();

        String message = event.getMessage().trim().toLowerCase();

        if (message.startsWith("!startbid") && CookieBotMain.CDBM.isPersonAlreadyInDatabase(username) && CookieBotMain.CDBM.getModStatusForPerson(username)){
            event.getChannel().send().message("Get your bids in!");
            biddingStarted = true;
        }else if (message.startsWith("!stopbid") && CookieBotMain.CDBM.isPersonAlreadyInDatabase(username) && CookieBotMain.CDBM.getModStatusForPerson(username)){
            event.getChannel().send().message("The bidding has ended. " + username + " has won the prize with his bid of " + currentBid + " cookies.");
            CookieBotMain.CDBM.takeCookiesFromUser(username,currentBid);
            currentBid = 0; currentTopUser = null;
            biddingStarted = false;
        }
        else if (message.startsWith("!bid") && biddingStarted){
            int amount = Integer.parseInt(message.replace("!bid","").trim());
            if (amount > currentBid && CookieBotMain.CDBM.isPersonAlreadyInDatabase(username) && CookieBotMain.CDBM.getCookieAmountForPerson(username) >= amount){
                currentBid = amount;
                currentTopUser = username;
            } else if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(username) && CookieBotMain.CDBM.getCookieAmountForPerson(username) < amount){
                event.getChannel().send().message("Sorry " + username + ", but you don't seem to have enough cookies for that bid");
            }
        }else if (message.startsWith("!bid") && !biddingStarted){
            event.getChannel().send().message("There is no bidding running right now.");
        }
    }
}
