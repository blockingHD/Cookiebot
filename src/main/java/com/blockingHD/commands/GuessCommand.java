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

    String currency = CookieBotMain.langOptions.getProperty("mainCurrency");
    String Storage = CookieBotMain.langOptions.getProperty("guessStorage");

    public GuessCommand() {
        subCommandHashMap.put("reset", new ResetSubCommand());
        subCommandHashMap.put(currency, new ShowSubCommand());
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
                event.respond(String.format("Max number of %s is ", currency) + module.getMaxGuess());
            }else {
                String username = event.getUser().getNick().trim().toLowerCase();
                // Found correct amount
                if (module.checkGuess(guess,username)){
                    event.getChannel().send().message(username + String.format(" has guessed the correct amount of %s in the %s!", currency, Storage));
                    CookieBotMain.CDBM.addCookiesToUser(username,20);
                }else {
                    // Incorrect guess
                    if (!module.isStillRunning()) {
                        event.respond(String.format("The %s have been found", currency));
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
                event.getChannel().send().message(String.format("The amount of %s in the %s were ", currency, Storage) + module.getMagicNumber());
                module.resetGuess();
            }
        }
    }
}
