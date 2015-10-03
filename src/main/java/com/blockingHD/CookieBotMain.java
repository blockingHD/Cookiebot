package com.blockingHD;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import java.io.IOException;

import static com.blockingHD.Referance.*;

/**
 * Created by blockingHD on 03/10/2015.
 */
public class CookieBotMain {

    public static void main(String[] args){
        new CookieBotMain();
    }

    Configuration<PircBotX> twitch = new Configuration.Builder<PircBotX>()
            .setName(NAME)
            .setLogin(LONGIN)
            .setServerPassword(PASS)
            .setAutoNickChange(true)
            .setServerHostname(HOST)
            .setServerPort(PORT)
            .addAutoJoinChannel(CHAN)
            .buildConfiguration();

    public CookieBotMain(){
        PircBotX bot = new PircBotX(twitch);
        try {
            bot.startBot();
        } catch (IOException | IrcException e) {
            e.printStackTrace();
        }
    }
}
