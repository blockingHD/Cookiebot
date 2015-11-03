package com.blockingHD.commands;

import com.blockingHD.CookieBotMain;
import com.blockingHD.chatPlugins.BiddingModule;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;

/**
 * Created by Mathieu on 3/11/2015.
 */
public class BiddingCommand implements Command {

    HashMap<String, Command> subCommandHashMap = new HashMap<>();

    BiddingModule biddingModule;

    public BiddingCommand(BiddingModule biddingModule) {
        this.biddingModule = biddingModule;

        subCommandHashMap.put("start", new StartBidSubCommand());
        subCommandHashMap.put("stop", new StopBidSubCommand());
        subCommandHashMap.put("sold", new ExecuteBidSubCommand());
    }

    @Override
    public void execute(MessageEvent event, String[] args) throws Exception {
        if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(event.getUser().getNick().trim().toLowerCase()) &&
                CookieBotMain.CDBM.getModStatusForPerson(event.getUser().getNick().toLowerCase().trim())){
            if ( args.length > 1 && subCommandHashMap.containsKey(args[1].toLowerCase().trim())){
                subCommandHashMap.get(args[1].toLowerCase().trim()).execute(event,args);
            }else
            {
                event.respond("!bidding <start| stop | sold>");
            }
        }else {
            event.respond("Invalid permissions");
        }
    }

    //Start
    private class StartBidSubCommand implements Command{

        @Override
        public void execute(MessageEvent event, String[] args) throws Exception {
            if (!biddingModule.startBidding()){
                event.getChannel().send().message("Let's not start two biddings at once?! TYVM");
            }
        }
    }

    //Stop
    private class StopBidSubCommand implements Command{

        @Override
        public void execute(MessageEvent event, String[] args) throws Exception {
            if (!biddingModule.stopBidding()){
                event.getChannel().send().message("Was I supposed to keep track of a bidding? Oh darn!");
            }
        }
    }

    //Sold
    private class ExecuteBidSubCommand implements Command{

        @Override
        public void execute(MessageEvent event, String[] args) throws Exception {
            String username = biddingModule.getTopBidderName();
            int amount = biddingModule.getTopBidAmount();
            if (biddingModule.executeBid()){
                event.getChannel().send().message("Congrats " + username + "! You won with your bid of " + amount + " cookies");
            }
        }
    }
}
