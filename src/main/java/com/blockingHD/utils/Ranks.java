package com.blockingHD.utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by MrKickkiller on 11/10/2015.
 */
@XmlRootElement(name = "Ranks")
public class Ranks {

    @XmlElement(name = "Rank")
    private Rank[] ranksArray;

    public Ranks() {
    }

    public Rank[] getRanks() {
        return ranksArray;
    }

    public void setRanks(Rank[] ranks) {
        this.ranksArray = ranks;
    }
}
