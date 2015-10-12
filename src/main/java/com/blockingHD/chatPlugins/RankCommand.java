package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.utills.Rank;
import com.blockingHD.utills.Ranks;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.TreeMap;

/**
 * Created by MrKickkiller on 7/10/2015.
 */
public class RankCommand extends ListenerAdapter<PircBotX> {
    static TreeMap<Integer,String> map = new TreeMap<>();

    public RankCommand() {
        String url = "ranks";
        if (CookieBotMain.devModeOn){
            url = "src/main/resources/" + url;
        }
        Ranks ranks = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Ranks.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            ranks = (Ranks) unmarshaller.unmarshal(new FileInputStream(url));
        } catch (JAXBException  | FileNotFoundException e) {
            CookieBotMain.printStaticMessageToAuthors();
            e.printStackTrace();
        }
        if (ranks == null){
            return;
        }

        for (Rank rank : ranks.getRanks()){
            map.put(rank.getMinimum(),rank.getName());
        }

    }

    boolean enabled = Boolean.parseBoolean(CookieBotMain.prop.getProperty("ranksEnabled"));

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (!enabled){
            return;
        }

        //Shows someones rank based for there cookie count.
        if (event.getMessage().startsWith("!rank")){
            String username = event.getUser().getNick();
            username = username.replace("!rank","").trim().split(" ")[0].toLowerCase();
            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(username)){
                int amountOfCookies = CookieBotMain.CDBM.getCookieAmountForPerson(username);
                int tracker = 0;
                while ( (Integer) map.descendingKeySet().toArray()[tracker] > amountOfCookies){
                    tracker ++;
                }
                if (username.equals("blockinghd") || username.equals("mrkickkiller")) {
                    event.getChannel().send().message(username + " is my creator!");
                }else if (username.equals("loneztar")) {
                    event.getChannel().send().message(username + " is GOD!");
                }else {
                    event.getChannel().send().message(username + " is a " + map.get(map.descendingKeySet().toArray()[tracker]));
                }
            }
        }
    }
}

