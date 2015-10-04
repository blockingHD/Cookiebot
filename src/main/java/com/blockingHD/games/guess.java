package com.blockingHD.games;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by blockingHD on 04/10/2015.
 */
public class guess extends ListenerAdapter<PircBotX> {

    int rand = 0;
    boolean isDone;
    List<String> users = new ArrayList<String>();

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String username = event.getUser().getNick();

        if (event.getMessage().toLowerCase().contains("!resetguess")){
            rand = new Random().nextInt(30);
            System.out.print(rand);
            users.clear();
            isDone = false;

        }else if (event.getMessage().toLowerCase().contains("!guess")){
            String guess = event.getMessage().replace("!guess ", "");
            if (isInt(guess)) {
                if (rand != Integer.parseInt(guess) && !isDone && !users.contains(username)) {
                    event.getChannel().send().message("Sorry that is incorrect " + username + " better look next time.");
                    users.add(username);
                } else if (rand == Integer.parseInt(guess) && !isDone && !users.contains(username)) {
                    event.getChannel().send().message("That is correct " + username + " here have " + rand + " cookies.");
                    isDone = true;
                } else if (isDone) {
                    event.getChannel().send().message("Sorry the cookies have been taken better look next time " + username + ".");
                } else if (users.contains(username)) {
                    event.getChannel().send().message("You have already guess " + username + ".");
                } else {
                    System.out.println("ERROR!");
                }
            }else{
                event.getChannel().send().message("Sorry that is not a number!");
            }
        }

    }

    private boolean isInt(String string){
        try {
            Integer.parseInt(string);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
