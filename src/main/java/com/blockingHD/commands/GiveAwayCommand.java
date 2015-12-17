package com.blockingHD.commands;

import com.blockingHD.CookieBotMain;
import com.blockingHD.chatPlugins.GiveAwayModule;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;

/**
 * Created by MrKickkiller on 17/12/2015.
 */
public class GiveAwayCommand implements Command {

    HashMap<String,Command> subCommandHashMap = new HashMap<>();

    GiveAwayModule module = new GiveAwayModule();

    @Override
    public void execute(MessageEvent event, String[] args) throws Exception {
        // Check for subcommands
        if (args.length >= 2 && subCommandHashMap.containsKey(args[1])){
            subCommandHashMap.get(args[1]).execute(event,args);
        }
    }

    public GiveAwayCommand() {
        subCommandHashMap.put("start", new GiveAwayStartSubCommand());
        subCommandHashMap.put("draw", new GiveAwayDrawSubCommand());
        subCommandHashMap.put("enter", new GiveAwayEnterSubCommand());
    }

    private class GiveAwayStartSubCommand implements Command{
        @Override
        public void execute(MessageEvent event, String[] args) throws Exception {
            String caller = event.getUser().getNick().toLowerCase().trim();
            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(caller) && CookieBotMain.CDBM.getModStatusForPerson(caller) && args.length >= 2){
                module.restart();
                module.setPrice(Integer.parseInt(args[2]));
                event.getChannel().send().message("New giveaway started");
            }else {
                event.respond("Something went wrong");
            }
        }
    }

    private class GiveAwayDrawSubCommand implements Command{
        @Override
        public void execute(MessageEvent event, String[] args) throws Exception {
            String caller = event.getUser().getNick().toLowerCase().trim();
            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(caller) && CookieBotMain.CDBM.getModStatusForPerson(caller)){
                String winner = module.randomlyDraw();
                event.getChannel().send().message( winner + " won the giveaway!");
            }
        }
    }

    private class GiveAwayEnterSubCommand implements Command{
        @Override
        public void execute(MessageEvent event, String[] args) throws Exception {
            String caller = event.getUser().getNick().toLowerCase().trim();
            module.userEnters(caller);
        }
    }

}
