package com.blockingHD.commands;

import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by MrKickkiller on 1/11/2015.
 */
public interface Command {

    void execute(MessageEvent event, String[] args);

}
