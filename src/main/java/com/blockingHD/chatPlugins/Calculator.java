package com.blockingHD.chatPlugins;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by MrKickkiller on 2/11/2015.
 */
public class Calculator {
    private ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");

    public String getValue(String expression){
        try {
            return  scriptEngine.eval(expression).toString();
        } catch (ScriptException e) {
            System.out.println("\u001B[34m" + "Bad Expression was given: " + expression + "\u001B[0m");
        }
        return "Not Available";
    }
}

