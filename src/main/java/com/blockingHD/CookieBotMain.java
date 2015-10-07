package com.blockingHD;

import com.blockingHD.chatPlugins.*;
import com.blockingHD.database.CookieDataBaseManipulator;
import com.blockingHD.database.CookieDatabase;
import com.blockingHD.games.GiveAway;
import com.blockingHD.games.guess;
import com.blockingHD.utills.Checkers;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.blockingHD.Referance.*;

/**
 * Created by blockingHD on 03/10/2015.
 */
public class CookieBotMain {

    public static final CookieDatabase DB = new CookieDatabase();
    public static final CookieDataBaseManipulator CDBM = new CookieDataBaseManipulator(DB);
    public static final Checkers CHECKERS = new Checkers();

    public static final Properties prop = new Properties();

    static {
        loadProperties();
    }

    Configuration<PircBotX> twitch = new Configuration.Builder<PircBotX>()
            .setName(NAME)
            .setLogin(LONGIN)
            .setServerPassword(PASS)
            .setAutoNickChange(true)
            .setServerHostname(HOST)
            .setServerPort(PORT)
            .addAutoJoinChannel(CHAN)
            .addListener(new UserCommands())
            .addListener(new guess())
            .addListener(new ModCommands())
            .addListener(new DatabaseUpdater())
            .addListener(new CookieGiver())
            .addListener(new GiveAway())
            .addListener(new Ranks())
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

    public static void loadProperties(){
        try (InputStream inputStream = new FileInputStream("src/main/java/com/blockingHD/cookieBotProperties")){
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
