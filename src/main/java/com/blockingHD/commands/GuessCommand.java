package com.blockingHD.commands;

import com.blockingHD.CookieBotMain;
import com.blockingHD.chatPlugins.GuessModule;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by MrKickkiller on 4/11/2015.
 */
public class GuessCommand implements Command {

    HashMap<String,Command> subCommandHashMap = new HashMap<>();

    Pattern guessMatcher = Pattern.compile("[0-9]+");

    GuessModule module = new GuessModule();

    int wonCookies = 20;

    public GuessCommand() {
        subCommandHashMap.put("reset", new ResetSubCommand());
        subCommandHashMap.put("cookies", new ShowSubCommand());
    }

    @Override
    public void execute(MessageEvent event, String[] args) throws Exception {
        // SubCommand
        if (subCommandHashMap.containsKey(args[1].toLowerCase().trim())){
            subCommandHashMap.get(args[1].toLowerCase().trim()).execute(event,args);
        }
        // Number entered
        else if (guessMatcher.matcher(args[1]).matches()){
            int guess = Integer.parseInt(args[1]);
            if (guess > module.getMaxGuess()){
                event.respond("Max number of cookies is " + module.getMaxGuess());
            }else {
                String username = event.getUser().getNick().trim().toLowerCase();
                // Found correct amount
                if (module.checkGuess(guess,username)){
                    event.getChannel().send().message(username + " has guessed the correct amount of cookies in the jar!");
                    CookieBotMain.CDBM.addCookiesToUser(username,20);
                }else {
                    // Incorrect guess
                    if (!module.isStillRunning()) {
                        event.respond("The cookies have been found");
                    } else if (module.getMagicNumber() != guess){
                        if (module.addUser(username)){
                            event.respond("Incorrect guess");
                        }else {
                            event.respond("Try again next stream");
                        }
                    }
                }
            }

        }else {
            event.respond("The numbers! What do they mean!");
        }
    }

    class ResetSubCommand implements Command{
        @Override
        public void execute(MessageEvent event, String[] args) throws Exception {
            String username = event.getUser().getNick().trim().toLowerCase();

            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(username) && CookieBotMain.CDBM.getModStatusForPerson(username)){
                module.resetGuess();
            }
        }
    }

    class ShowSubCommand implements Command{

        @Override
        public void execute(MessageEvent event, String[] args) throws Exception {
            String username = event.getUser().getNick().trim().toLowerCase();

            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(username) && CookieBotMain.CDBM.getModStatusForPerson(username)){
                event.getChannel().send().message("The amount of cookies in the jar were " + module.getMagicNumber());
                module.resetGuess();
            }
        }
    }
}
