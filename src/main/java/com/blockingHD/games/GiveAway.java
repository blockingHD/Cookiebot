package com.blockingHD.games;

import com.blockingHD.CookieBotMain;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;
import java.util.Random;

import static com.blockingHD.CookieBotMain.CDBM;

/**
 * Created by blockingHD on 06/10/2015.
 */
@Deprecated
public class GiveAway extends ListenerAdapter<PircBotX> {

    /*
    * The enabled status of this module.
     */
    boolean enabled = Boolean.parseBoolean(CookieBotMain.prop.getProperty("giveawayEnabled"));

    String keyword;
    boolean hasStarted;
    ArrayList<String> users = new ArrayList<String>();

    @Override
    public void onMessage(MessageEvent<PircBotX> event) throws Exception {
        if (!enabled){
            return;
        }

        String username = event.getUser().getNick().toLowerCase().trim();

        // Starts giveaway.
        if (event.getMessage().toLowerCase().startsWith("!giveaway")){
            if (CDBM.isPersonAlreadyInDatabase(username) && CDBM.getModStatusForPerson(username)) {
                keyword = event.getMessage().replace("!giveaway", "").toLowerCase().trim();

                if (!keyword.isEmpty()) {
                    hasStarted = true;
                    event.getChannel().send().message("Giveaway has started with the keyword: " + keyword + ". Type this in chat at the cost of 10 cookies!");
                }else {
                    event.getChannel().send().message("There was no keyword attached");
                }
            }

        //Draws the giveaway.
        } else if (event.getMessage().startsWith("!drawgiveaway")){
            if (CDBM.isPersonAlreadyInDatabase(username) && CDBM.getModStatusForPerson(username) && hasStarted){
                if (users.isEmpty()){
                    event.getChannel().send().message("Nobody entered the giveaway! :'(");
                    return;
                }
                int rand = new Random().nextInt(users.size());
                String winner = users.get(rand);
                event.getChannel().send().message("The winner of the giveaway is: " + winner + "!!!!!");

            }
        //Checks for the keyword and enters them for the giveaway.
        }else if (hasStarted && !keyword.isEmpty()){
            if (event.getMessage().toLowerCase().contains(keyword)) {
                if (CDBM.isPersonAlreadyInDatabase(username) && CDBM.getCookieAmountForPerson(username) >= 10 && !users.contains(username)) {
                    CDBM.takeCookiesFromUser(username, 10);
                    users.add(username);
                }
            }
        }
    }
}
