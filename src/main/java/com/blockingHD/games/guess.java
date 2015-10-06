package com.blockingHD.games;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.blockingHD.CookieBotMain.CDBM;

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

        // Moderator resets the guess-ing
        if (event.getMessage().toLowerCase().contains("!resetguess") &&
                CDBM.isPersonAlreadyInDatabase(username.trim()) &&
                CDBM.getModStatusForPerson(username.trim())){

            rand = new Random().nextInt(30);
            System.out.print(rand);
            users.clear();
            isDone = false;
            event.getChannel().send().message("A new round of cookie-guessing has begon. Get to them before Loneztar does!");

            // Normal viewer tries to reset the guess
        }else if (CDBM.isPersonAlreadyInDatabase(username.trim()) &&
                !CDBM.getModStatusForPerson(username.trim()) &&
                event.getMessage().toLowerCase().contains("!resetguess")){

            event.getChannel().send().message("Nice try " + username + ", but better luck next time!");

        // Normal user or moderator wants to guess.
        } else if (event.getMessage().toLowerCase().contains("!guess")){
            String guess = event.getMessage().replace("!guess ", "").trim();
            if (isInt(guess)) {
                if (rand != Integer.parseInt(guess) && !isDone && !users.contains(username)) {
                    if (Integer.parseInt(guess) <= 30) {
                        event.getChannel().send().message("Sorry that is incorrect " + username + " better luck next time.");
                        users.add(username);
                    }else {
                        event.getChannel().send().message("Please choose a number between 0 and 30");
                    }
                } else if (rand == Integer.parseInt(guess) && !isDone && !users.contains(username)) {
                    event.getChannel().send().message("That is correct " + username + "! Here have " + rand + " cookies.");
                    CDBM.addCookiesToUser(username.trim(), 20);
                    isDone = true;
                } else if (isDone) {
                    event.getChannel().send().message("Sorry the cookies have been taken ,better luck next time " + username + ".");
                } else if (users.contains(username)) {
                    event.getChannel().send().message("You have already guessed " + username + ".");
                } else {
                    System.out.println("ERROR!");
                }
            // Reveal the amount of cookies in the jar and sets it as completed.
            }else if (guess == "cookies" &&
                    CDBM.isPersonAlreadyInDatabase(username.trim()) &&
                    CDBM.getModStatusForPerson(username.trim())){
                event.getChannel().send().message("The amount of cookies in the bunny's secret jar was " + rand + "!");
                isDone = true;
            }
            else{
                event.getChannel().send().message("Sorry that is not a number!");
            }
        }

    }

    private boolean isInt(String string){
        try {
            Integer.parseInt(string.trim());
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
