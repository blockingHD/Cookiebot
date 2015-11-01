package com.blockingHD.commands;

import org.pircbotx.hooks.events.MessageEvent;

import java.util.regex.Pattern;

/**
 * Created by MrKickkiller on 2/11/2015.
 */
public class CalculatorCommand implements Command {
    Calculator calc = new Calculator();
    Pattern pattern = Pattern.compile("[0-9+*-/()%]*");

    @Override
    public void execute(MessageEvent event, String[] args) {
        String ask = "" ;
        for (int i = 1; i < args.length; i++) {
            if (pattern.matcher(args[i]).matches()){
                ask += args[i];
            }
        }
        System.out.println(ask);
        String result = calc.getValue(ask);
        event.respond("The answer is " + result);
    }


}
