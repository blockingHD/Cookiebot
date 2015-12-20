package com.blockingHD.chatPlugins;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by MrKickkiller on 4/11/2015.
 */
public class GuessModule {

    int magicNumber;
    boolean guessNotFound;

    Random rand = new Random();

    int maxGuess = 30;

    Set<String> users = new HashSet<>();

    static GuessModule instance;

    public GuessModule() {
        magicNumber = rand.nextInt(maxGuess);
        guessNotFound = true;
        instance = this;
    }

    public boolean checkGuess(int userGuess, String user){
        if (guessNotFound && userGuess == magicNumber && !users.contains(user)){
            guessNotFound = false;
            return true;
        }
        return false;
    }

    public boolean resetGuess(){
        magicNumber = rand.nextInt(maxGuess);
        users.clear();
        guessNotFound = true;
        return true;
    }

    public int getMagicNumber(){
        return magicNumber;
    }

    public boolean isStillRunning(){
        return guessNotFound;
    }

    public boolean userInUsers(String username){
        return users.contains(username);
    }

    public boolean guessIsOverMax(int guess){
        return guess > this.maxGuess;
    }

    public int getMaxGuess(){
        return maxGuess;
    }

    public boolean addUser(String username){
        return users.add(username);
    }
}
