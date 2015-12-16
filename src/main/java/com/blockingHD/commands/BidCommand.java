package com.blockingHD.commands;

import com.blockingHD.chatPlugins.BiddingModule;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by MrKickkiller on 3/11/2015.
 */
public class BidCommand implements Command {

    BiddingModule biddingModule;

    public BidCommand(BiddingModule biddingModule) {
        this.biddingModule = biddingModule;
    }

    @Override
    public void execute(MessageEvent event, String[] args) throws Exception {
        if (! biddingModule.registerBid(biddingModule.createBid(event.getUser(),Integer.parseInt(args[1])))){
            event.respond("Something went wrong with your bid. Try again (later)");
        }
    }
}
