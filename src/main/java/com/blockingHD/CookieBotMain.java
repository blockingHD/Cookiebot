package com.blockingHD;

import com.blockingHD.chatPlugins.CookieGiver;
import com.blockingHD.chatPlugins.DatabaseUpdater;
import com.blockingHD.chatPlugins.ModCommands;
import com.blockingHD.chatPlugins.UserCommands;
import com.blockingHD.database.CookieDataBaseManipulator;
import com.blockingHD.database.CookieDatabase;
import com.blockingHD.games.Guess;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

import static com.blockingHD.Referance.*;

/**
 * Created by blockingHD on 03/10/2015.
 */
public class CookieBotMain {

    public static final CookieDatabase DB = new CookieDatabase();
    public static final CookieDataBaseManipulator CDBM = new CookieDataBaseManipulator(DB);

    Configuration<PircBotX> twitch = new Configuration.Builder<PircBotX>()
            .setName(NAME)
            .setLogin(LONGIN)
            .setServerPassword(PASS)
            .setAutoNickChange(true)
            .setServerHostname(HOST)
            .setServerPort(PORT)
            .addAutoJoinChannel(CHAN)
            .addListener(new UserCommands())
            .addListener(new Guess())
            .addListener(new ModCommands())
            .addListener(new DatabaseUpdater())
            .addListener(new CookieGiver())
            .buildConfiguration();


    public CookieBotMain(){
        PircBotX bot = new PircBotX(twitch);
        try {
            bot.startBot();
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new CookieBotMain();
    }

    public static void printStaticMessageToAuthors(){
        System.out.println("Notify MrKickkiller or BlockingHD this happened.");
        System.out.println("You should provide a log of what happened in the last 10 minutes!");
        System.out.println("Use a github gist or a pastebin for this!");
    }
}
