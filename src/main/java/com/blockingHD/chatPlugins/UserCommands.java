package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import static com.blockingHD.CookieBotMain.*;

/**
 * Created by blockingHD on 03/10/2015.
 */
public class UserCommands extends ListenerAdapter<PircBotX> {

    String calculator = prop.getProperty("enableCalculator");
    String commandlist = prop.getProperty("commandListURL");
    String commandEnable = prop.getProperty("showViewersCommandList");

    boolean enabled = Boolean.parseBoolean(CookieBotMain.prop.getProperty("userCommandsEnabled"));


    //To Do: add commands and returns to props file
    
    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (!enabled){
            return;
        }
        if(event.getMessage().equalsIgnoreCase("!hello")) {
            event.getChannel().send().message(event.getUser().getNick() + " Hi how are you today?");
        }else if(event.getMessage().startsWith("!cookies give")){
            // !cookies give username amount
            String receiverAndCount = event.getMessage().replace("!cookies give","").trim();
            String receiver = receiverAndCount.split(" ")[0];
            int amount = Integer.parseInt(receiverAndCount.split(" ")[1]);
            String sender = event.getUser().getNick().toLowerCase().trim();

            if (CDBM.isPersonAlreadyInDatabase(sender.trim()) && CDBM.takeCookiesFromUser(sender.trim(), amount)){
                CDBM.addCookiesToUser(receiver.trim(),amount);
                event.getChannel().send().message( sender + " has given " + receiver + " " + amount+ " of cookies");
            }else {
                event.getChannel().send().message("Transaction failed. " + sender + " : Make sure you have enough cookies");
            }

        }else if (event.getMessage().startsWith("!cookies")){

            /*
            * Retrieves the cookies from a person and displays them.
            *
            * Uses:
            *  - !cookies = Display the user's cookies, that uses the command
            *  - !cookies <Username> = Display the cookies of Username
            *  - !cookies <Username> <Additional text for a ping> = Same as !cookies <Username>, but allows for more text
            *                                                       to be added.
            *
            */

            String username = event.getMessage().replace("!cookies","");
            username = username.trim(); // Trim all leading and following spaces, because they are nasty buggers.
            int indexOfSpace = username.indexOf(' ');
            if (indexOfSpace != -1){
                username = username.substring(0,indexOfSpace);
            }
            if (username.length() == 0){
                username = event.getUser().getNick();
            }
            int amount = CDBM.getCookieAmountForPerson(username);
            event.getChannel().send().message(username + " has " + amount + " cookies in their secret stash");
        }else if (event.getMessage().startsWith("!cal") && calculator.equalsIgnoreCase("true")){

            String[] calculation = event.getMessage().replace("!cal", "").trim().split("");

            if (CHECKERS.isInt(calculation[1]) && CHECKERS.isInt(calculation[1])) {
                if (calculation[2].contains("*")) {
                    event.getChannel().send().message(Integer.toString(Integer.parseInt(calculation[1]) * Integer.parseInt(calculation[3])));
                } else if (calculation[2].contains("/")) {
                    event.getChannel().send().message(Float.toString(Float.parseFloat(calculation[1]) / Float.parseFloat(calculation[3])));
                } else if (calculation[2].contains("+")) {
                    event.getChannel().send().message(Integer.toString(Integer.parseInt(calculation[1]) + Integer.parseInt(calculation[3])));
                } else if (calculation[2].contains("-")) {
                    event.getChannel().send().message(Integer.toString(Integer.parseInt(calculation[1]) - Integer.parseInt(calculation[3])));
                }
            }

        }else if (event.getMessage().startsWith("!commands")){
            if (commandEnable.equals("true")){
                event.getChannel().send().message("Command list can  be found here: " + commandlist);
            }

        }
    }

}
