package com.blockingHD.utills;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MrKickkiller on 6/10/2015.
 */
public class TwitchApiReturn {
    class Chatters{
        @SerializedName("moderators")
        String[] moderators;
        @SerializedName("staff")
        String[] staff;
        @SerializedName("admins")
        String[] admins;
        @SerializedName("global_mods")
        String[] global_mods;
        @SerializedName("viewers")
        String[] viewers;

        public Chatters() {
        }

        public String[] getModerators() {
            return moderators;
        }

        public void setModerators(String[] moderators) {
            this.moderators = moderators;
        }

        public String[] getStaff() {
            return staff;
        }

        public void setStaff(String[] staff) {
            this.staff = staff;
        }

        public String[] getAdmins() {
            return admins;
        }

        public void setAdmins(String[] admins) {
            this.admins = admins;
        }

        public String[] getGlobal_mods() {
            return global_mods;
        }

        public void setGlobal_mods(String[] global_mods) {
            this.global_mods = global_mods;
        }

        public String[] getViewers() {
            return viewers;
        }

        public void setViewers(String[] viewers) {
            this.viewers = viewers;
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
