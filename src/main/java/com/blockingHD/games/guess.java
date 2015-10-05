package com.blockingHD.games;

import com.blockingHD.CookieBotMain;
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

    int rand = new Random().nextInt(30);
    boolean isDone;
    List<String> users = new ArrayList<String>();

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String username = event.getUser().getNick();

        if (event.getMessage().toLowerCase().contains("!resetguess") && CookieBotMain.CDBM.getModStatusForPerson(username)){
            rand = new Random().nextInt(30);
            System.out.print(rand);
            users.clear();
            isDone = false;
            event.getChannel().send().message("A new round of cookie-guessing has begon. Get to them before Loneztar does!");

        }else if (event.getMessage().toLowerCase().contains("!resetguess") && CookieBotMain.CDBM.isPersonAlreadyInDatabase(username.trim()) && !CookieBotMain.CDBM.getModStatusForPerson(username)){
            event.getChannel().send().message("Nice try " + username + ", but better luck next time!");
        } else if (event.getMessage().toLowerCase().contains("!guess")){
            String guess = event.getMessage().replace("!guess ", "");
            if (isInt(guess)) {
                if (rand != Integer.parseInt(guess) && !isDone && !users.contains(username)) {
                    event.getChannel().send().message("Sorry that is incorrect " + username + " better luck next time.");
                    users.add(username);
                } else if (rand == Integer.parseInt(guess) && !isDone && !users.contains(username)) {
                    event.getChannel().send().message("That is correct " + username + "! Here have " + rand + " cookies.");
                    isDone = true;
                } else if (isDone) {
                    event.getChannel().send().message("Sorry the cookies have been taken ,better luck next time " + username + ".");
                } else if (users.contains(username)) {
                    event.getChannel().send().message("You have already guessed " + username + ".");
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
