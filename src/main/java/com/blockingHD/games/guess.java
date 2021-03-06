package com.blockingHD.games;

import com.blockingHD.CookieBotMain;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.blockingHD.CookieBotMain.*;

/**
 * Created by blockingHD on 04/10/2015.
 */
@Deprecated
public class guess extends ListenerAdapter<PircBotX> {
    /*
    * Settings for this module
     */
    int maxGuessingNumber = Integer.parseInt(prop.getProperty("maxGuessAmount"));
    int amountOfCookiesWon = Integer.parseInt(prop.getProperty("amountOfCookiesWon"));

    boolean enabled = Boolean.parseBoolean(CookieBotMain.prop.getProperty("guessEnabled"));

    /*
    * Variables / Objects needed for this module to function
     */
    int rand = new Random().nextInt(maxGuessingNumber);
    boolean isDone;
    List<String> users = new ArrayList<String>();

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (!enabled){
            return;
        }

        String username = event.getUser().getNick().trim();

        // Moderator resets the Guess-ing
        if (event.getMessage().toLowerCase().startsWith("!resetguess") &&
                CDBM.isPersonAlreadyInDatabase(username.trim()) &&
                CDBM.getModStatusForPerson(username.trim())){

            this.resetGuessing();
            event.getChannel().send().message("A new round of cookie-guessing has begon. Get to them before "+ event.getChannel().getName().replace("#","").toLowerCase().trim() +" does!");

        // Normal viewer tries to reset the Guess
        }else if (CDBM.isPersonAlreadyInDatabase(username.trim()) &&
                !CDBM.getModStatusForPerson(username) &&
                event.getMessage().toLowerCase().startsWith("!resetguess")){

            event.getChannel().send().message("Nice try " + username + ", but better luck next time!");

        // Normal user or moderator wants to Guess.
        } else if (event.getMessage().toLowerCase().startsWith("!guess")){
            String guess = event.getMessage().replace("!guess ", "").trim();
            if (CHECKERS.isInt(guess)) {
                if (rand != Integer.parseInt(guess) && !isDone && !users.contains(username)) {
                    if ( 0 <= Integer.parseInt(guess) &&Integer.parseInt(guess) <= maxGuessingNumber) {
                        event.getChannel().send().message("Sorry that is incorrect " + username + ", better luck next time!");
                        users.add(username);
                    }else {
                        event.getChannel().send().message("Please choose a number between 0 and " + maxGuessingNumber);
                    }
                } else if (rand == Integer.parseInt(guess) && !isDone && !users.contains(username)) {
                    event.getChannel().send().message("That is correct " + username + "! Here have " + amountOfCookiesWon + " cookies.");
                    CDBM.addCookiesToUser(username.trim(), amountOfCookiesWon);
                    isDone = true;
                } else if (isDone) {
                    event.getChannel().send().message("Sorry the cookies have been taken, better luck next time " + username + ".");
                } else if (users.contains(username)) {
                    event.getChannel().send().message("You have already guessed " + username + ".");
                } else {
                    System.out.println("ERROR!");
                }
            // Reveal the amount of cookies in the jar and sets it as completed.
            }else if (guess.equals("cookies") &&
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

    private void resetGuessing(){
        rand = new Random().nextInt(maxGuessingNumber);
        users.clear();
        isDone = false;
    }

}
