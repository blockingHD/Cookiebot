package com.blockingHD.games;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.Random;

import static com.blockingHD.CookieBotMain.CDBM;

/**
 * Created by blockingHD on 06/10/2015.
 */
public class GiveAway extends ListenerAdapter<PircBotX> {

    String keyword;
    boolean hasStarted;
    ArrayList<String> users = new ArrayList<String>();

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        String username = event.getUser().getNick().toLowerCase();

        if (event.getMessage().toLowerCase().contains("!giveaway")){
            if (CDBM.getModStatusForPerson(username)) {
                keyword = event.getMessage().replace("!giveaway", "").trim();
                hasStarted = true;

                event.getChannel().send().message("Giveaway has started with the keyword: " + keyword + " type this is chat at the cost of 10 cookies!!");

            }
        }else if (hasStarted && !keyword.isEmpty()){
            if (event.getMessage().toLowerCase().contains(keyword)) {
                if (CDBM.getCookieAmountForPerson(username) >= 10 && !users.contains(username)) {
                    CDBM.takeCookiesFromUser(username, 10);
                    users.add(username);
                }
            }
        }else if (event.getMessage().contains("!drawgiveaway")){
            if (CDBM.getModStatusForPerson(username) && hasStarted){
                int rand = new Random().nextInt(users.size());

                String winner = users.get(rand);

                event.getChannel().send().message("The winner of the giveaway is: " + winner + "!!!!!");

            }
        }
    }
}
