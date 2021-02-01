package com.justin.Utilities;

import java.util.TimerTask;

public class Timers extends TimerTask{
    private int time = 0;

    public void run() {
        time++;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
