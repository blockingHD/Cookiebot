package com.blockingHD.utils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MrKickkiller on 20/12/2015.
 * Special kind of timer to have a "running" status
 */
public class CookieTimer extends Timer {
    boolean isOn;

    public CookieTimer() {
        super();
    }

    @Override
    public void cancel() {
        isOn=false;
        super.cancel();
    }

    @Override
    public int purge() {
        isOn=false;
        return super.purge();
    }

    @Override
    public void schedule(TimerTask task, long delay) {
        isOn=true;
        super.schedule(task, delay);
    }

    @Override
    public void schedule(TimerTask task, Date time) {
        isOn=true;
        super.schedule(task, time);
    }

    @Override
    public void schedule(TimerTask task, long delay, long period) {
        isOn=true;
        super.schedule(task, delay, period);
    }

    @Override
    public void schedule(TimerTask task, Date firstTime, long period) {
        isOn=true;
        super.schedule(task, firstTime, period);
    }

    @Override
    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        isOn=true;
        super.scheduleAtFixedRate(task, delay, period);
    }

    @Override
    public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
        isOn=true;
        super.scheduleAtFixedRate(task, firstTime, period);
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }
}
