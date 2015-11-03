package com.blockingHD.commands;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;

/**
 * Created by MrKickkiller on 1/11/2015.
 */
public class CommandHandler extends ListenerAdapter<PircBotX> {

    private HashMap<String, Command> stringCommandHashMap = new HashMap<>();

    public CommandHandler() {
        stringCommandHashMap.put("!cookies", new CookiesCommand());
        stringCommandHashMap.put("!hi", new HelloCommand());
        stringCommandHashMap.put("!calc", new CalculatorCommand());
        stringCommandHashMap.put("!mod", new ModCommand());
        stringCommandHashMap.put("!unmod", new UnmodCommand());
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        long time= System.nanoTime();
        String[] choppedCommand = event.getMessage().toLowerCase().split(" ");
        if (stringCommandHashMap.containsKey(choppedCommand[0])){
            stringCommandHashMap.get(choppedCommand[0]).execute(event, choppedCommand);
        }
        System.out.println("HashTime = "+ (System.nanoTime() - time));
    }
}
