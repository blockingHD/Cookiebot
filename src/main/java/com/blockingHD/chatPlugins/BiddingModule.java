package com.blockingHD.chatPlugins;

import com.blockingHD.CookieBotMain;
import com.blockingHD.exceptions.OutOfCookieException;
import org.pircbotx.User;

/**
 * Created by MrKickkiller on 3/11/2015.
 */
public class BiddingModule {

    boolean hasStarted;

    Bid topBid;

    public boolean registerBid(Bid bid){
        if (hasStarted && CookieBotMain.CDBM.isPersonAlreadyInDatabase(bid.getBidder()) && CookieBotMain.CDBM.getCookieAmountForPerson(bid.getBidder()) >= bid.getBidAmount()){
            if (topBid == null || topBid.getBidAmount() < bid.getBidAmount()){
                topBid = bid;
            }
            return true;
        }
        return false;
    }

    public boolean executeBid(){
        try {
            CookieBotMain.CDBM.takeCookiesFromUser(topBid.getBidder(), topBid.bidAmount);
            topBid.resetBid();
            hasStarted = false;
            return true;
        } catch (OutOfCookieException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean startBidding(){
        if (!hasStarted){
            hasStarted = true;
            return true;
        }
        return false;
    }

    public boolean stopBidding(){
        if (hasStarted){
            topBid.resetBid();
            hasStarted = false;
            return true;
        }
        return false;
    }

    public Bid createBid(User user, int amount){
        return new Bid(user,amount);
    }

    public String getTopBidderName(){
        return topBid.getBidder();
    }

    public int getTopBidAmount(){
        return topBid.getBidAmount();
    }



    public class Bid{
        private int bidAmount;
        private User bidder;

        public Bid(User bidder, int bidAmount) {
            this.bidder = bidder;
            this.bidAmount = bidAmount;
        }

        public String getBidder(){
            return bidder.getNick().trim().toLowerCase();
        }
        public int getBidAmount(){
            return bidAmount;
        }

        private void resetBid(){
            bidder = null;
            bidAmount = 0;
        }

    }
}
