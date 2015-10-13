package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.Referance;
import com.blockingHD.utils.JSONManipulator;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MrKickkiller on 9/10/2015.
 *
 * Contains test code with proposed re-working method for all commands.
 * Functionality based on java.lang.reflection
 */
public class TestCommands extends ListenerAdapter<PircBotX>{

    private HashMap<String,Method> map = new HashMap<>();

    private Channel channel;

    public TestCommands() {
        try {
            map.put("!hi", TestCommands.class.getMethod("hiCommand", String.class));
            map.put("!bye", TestCommands.class.getMethod("byeCommand", String.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            CookieBotMain.printStaticMessageToAuthors();
        }
    }

    @SuppressWarnings("unused")
    public void hiCommand(String name){
        channel.send().message(name);
    }

    @SuppressWarnings("unused")
    public void byeCommand(String name){
        List<String> list = JSONManipulator.getChatters("http://tmi.twitch.tv/group/user/"+ channel.getName().replace("#","").toLowerCase().trim() +"/chatters");
        String output = "Bye";
        for (String s: list ){
            if (! s.trim().toLowerCase().equals(Referance.NAME.toLowerCase().trim()) ){
                output += ", " + s;
            }
        }
        output += "! Thanks for joining the stream";
        if (output.length() > Integer.parseInt(CookieBotMain.prop.getProperty("maxLenghtOfBye"))){
            output = "Too many people to say thanks to! Hope you all had fun this stream, and see you next time!";
        }
        channel.send().message(output);
    }

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        channel = event.getChannel();
        String[] message = event.getMessage().toLowerCase().trim().split(" ");
        if (map.containsKey(message[0])){
            map.get(message[0]).invoke(this, "Hi "+ event.getUser().getNick() + "!");
        }
    }
}
