package com.blockingHD.utills;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by MrKickkiller on 6/10/2015.
 */
public class TwitchApiReturn {
    class Chatters{
        @SerializedName("moderators")
        ArrayList<String> moderators;
        @SerializedName("staff")
        ArrayList<String> staff;
        @SerializedName("admins")
        ArrayList<String> admins;
        @SerializedName("global_mods")
        ArrayList<String> global_mods;
        @SerializedName("viewers")
        ArrayList<String> viewers;

        public Chatters() {
        }

        public ArrayList<String> getModerators() {
            return moderators;
        }

        public void setModerators(ArrayList<String> moderators) {
            this.moderators = moderators;
        }

        public ArrayList<String> getStaff() {
            return staff;
        }

        public void setStaff(ArrayList<String> staff) {
            this.staff = staff;
        }

        public ArrayList<String> getAdmins() {
            return admins;
        }

        public void setAdmins(ArrayList<String> admins) {
            this.admins = admins;
        }

        public ArrayList<String> getGlobal_mods() {
            return global_mods;
        }

        public void setGlobal_mods(ArrayList<String> global_mods) {
            this.global_mods = global_mods;
        }

        public ArrayList<String> getViewers() {
            return viewers;
        }

        public void setViewers(ArrayList<String> viewers) {
            this.viewers = viewers;
        }

        public ArrayList<String> getAllInChat(){
            viewers.addAll(getGlobal_mods());
            viewers.addAll(getAdmins());
            viewers.addAll(getStaff());
            viewers.addAll(moderators);
            return viewers;
        }
    }

    class Links{
        public Links() {
        }
    }
    @SerializedName("_links")
    private Links _links;

    @SerializedName("chatter_count")
    private int chatter_count;

    @SerializedName("chatters")
    private Chatters chatters;

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    public int getChatter_count() {
        return chatter_count;
    }

    public void setChatter_count(int chatter_count) {
        this.chatter_count = chatter_count;
    }

    public Chatters getChatters() {
        return chatters;
    }

    public void setChatters(Chatters chatters) {
        this.chatters = chatters;
    }
}
