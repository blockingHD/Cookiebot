package com.blockingHD.commands;

import com.blockingHD.CookieBotMain;
import com.blockingHD.utils.Rank;
import com.blockingHD.utils.Ranks;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.events.MessageEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Created by MrKickkiller on 7/10/2015.
 */
public class RankCommand implements Command{

    static TreeMap<Integer,String> map = new TreeMap<>();

    boolean enabled = Boolean.parseBoolean(CookieBotMain.prop.getProperty("ranksEnabled"));

    Map<String,String> tributeMap = new HashMap<>();

    public RankCommand() {
        Ranks ranks = null;
        String url = "ranks";
        if (CookieBotMain.devModeOn){
            url = "src/main/resources/" + url;
        }

        // Load in the Java-Objects from XML

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

        // Transform the Java-Objects into the map used
        for (Rank rank : ranks.getRanks()){
            map.put(rank.getMinimum(),rank.getName());
        }


        //tributeMap.put("mrkickkiller", " creator");
        tributeMap.put("blockinghd", " creator");
        //TODO: XML to set custom tribute tags.
    }

    @Deprecated
    private void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (!enabled){
            return;
        }

        //Shows someone's rank based on their cookie count
        if (event.getMessage().startsWith("!rank")){
            String username = event.getUser().getNick();
            username = username.replace("!rank","").trim().split(" ")[0].toLowerCase();
            if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(username)){
                int amountOfCookies = CookieBotMain.CDBM.getCookieAmountForPerson(username);
                int tracker = 0;
                while ( (Integer) map.descendingKeySet().toArray()[tracker] > amountOfCookies){
                    tracker ++;
                }
                // Credit :P
                if (username.equals("blockinghd") || username.equals("mrkickkiller")) {
                    event.getChannel().send().message(username + " is my creator!");
                }else if (username.equals("jake_evans")){
                    event.getChannel().send().message(username + " is demi-GOD");
                // Channel name now automatically finds broadcaster.
                }else if (username.equals(event.getChannel().getName().toLowerCase().trim().replace("#",""))) {
                    event.getChannel().send().message(username + " is GOD!");
                }else {
                    event.getChannel().send().message(username + " is a " + map.get(map.descendingKeySet().toArray()[tracker]));
                }
            }
        }
    }

    Pattern userMatcher = Pattern.compile("[^_][A-z0-9_]*");

    @Override
    public void execute(MessageEvent event, String[] args) throws Exception {
        String userName = event.getUser().getNick().toLowerCase().trim();
        if (args.length > 1 && userMatcher.matcher(args[1]).matches()){
            userName = args[1].toLowerCase().trim();
        }
        if (tributeMap.containsKey(userName)){
            event.getChannel().send().message(userName + " is a " + tributeMap.get(userName));
        }else if (CookieBotMain.CDBM.isPersonAlreadyInDatabase(userName)){
            event.getChannel().send().message(userName + " is a " + determineRank(CookieBotMain.CDBM.getCookieAmountForPerson(userName)) );
        }
    }

    public String determineRank(int cookies){
        int tracker = 0;
        while ( (Integer) map.descendingKeySet().toArray()[tracker] > cookies){
            tracker ++;
        }
        return map.get(map.descendingKeySet().toArray()[tracker]);
    }

}

