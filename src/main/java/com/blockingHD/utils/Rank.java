package com.blockingHD.utils;

/**
 * Created by Mathieu on 11/10/2015.
 */
public class Rank {
        //@XmlElement(name = "minimum")
        private int minimum;

        //@XmlElement(name = "rankName")
        public String rankName;

        public Rank() {
        }

        public int getMinimum() {
            return minimum;
        }

        public void setMinimum(int minimumCount) {
            this.minimum = minimumCount;
        }

        public String getName() {
            return rankName;
        }

        public void setName(String name) {
            this.rankName = name;
        }
    }
