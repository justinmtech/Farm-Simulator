package com.justin.Entities;

import com.justin.Interfaces.FarmInterface;
import com.justin.Utilities.Timers;

import java.util.Timer;

public class Farm implements FarmInterface {
    private int time;
    private String farmType;
    private int farmLevel;
    private final Timer farmTimer;
    private final Timers keepFarmTime;
    private int farmLocation;
    private int cost;
    private int upgradeCost;

    public Farm() {
        this.farmTimer = new Timer();
        this.keepFarmTime = new Timers();
        startFarmTimer();
    }

    public Farm(String farmType, int farmLocation) {
        this.time = 0;
        this.farmType = farmType;
        this.cost = getFarmCost(farmType);
        this.upgradeCost = getFarmCost(farmType) * 10;
        this.farmLocation = farmLocation;
        this.farmLevel = 1;
        this.farmTimer = new Timer();
        this.keepFarmTime = new Timers();
        startFarmTimer();
    }

    public Farm(String farmType, int farmLocation, int farmLevel) {
        this.time = 0;
        this.farmType = farmType;
        this.cost = getFarmCost(farmType);
        this.upgradeCost = getFarmCost(farmType) * 10;
        this.farmLocation = farmLocation;
        this.farmLevel = farmLevel;
        this.farmTimer = new Timer();
        this.keepFarmTime = new Timers();
        startFarmTimer();
    }

    public double calculateFarmProfit(String farmType, int farmLevel) {
        double profit = 0;
        int time = getTime();
        setTime(time);
        profit += getLevelMultiplier(farmLevel) * ((time * 10) * getFarmMultiplier(farmType));
        resetTime();

        return profit;
    }

    private double getLevelMultiplier(int level) {
        double multiplier;
            if (level == 1) multiplier = 1.01;
            else if (level == 2) multiplier = 1.02;
            else if (level == 3) multiplier = 1.04;
            else if (level == 4) multiplier = 1.06;
            else multiplier = 1.12;

        return multiplier;
    }

    private double getFarmMultiplier(String type) {
        double multiplier = 0;
        switch (type) {
            case "C": multiplier = 1.01; break;
            case "S": multiplier = 1.02; break;
            case "G": multiplier = 1.08; break;
            case "D": multiplier = 1.24;
        }
        return multiplier;
    }

    public int getTime() {
        time = keepFarmTime.getTime();
        return time;
    }

    public void setTime(int newTime) {
        time = newTime;
        keepFarmTime.setTime(time);
    }

    public int getFarmCost(String farmType) {
        int cost = 0;
        switch (farmType) {
            case "C": cost = 100; break;
            case "S": cost = 5000; break;
            case "G": cost = 50000; break;
            case "D": cost = 250000;
        }
        return cost;
    }

    public int getUpgradeCost(String farmType) {
        upgradeCost = getFarmCost(farmType) * 2;
        return upgradeCost;
    }

    public void startFarmTimer() {
        farmTimer.schedule(keepFarmTime, 1000, 1000);
    }

    public void cancelTimer() { farmTimer.cancel(); }

    private void resetTime() { keepFarmTime.setTime(0);}

    public String getFarmType() {
        return farmType;
    }

    public void setFarmType(String farmType) {
        this.farmType = farmType;
    }

    public int getFarmLevel() {
        return farmLevel;
    }

    public void setFarmLevel(int newLevel) {
        this.farmLevel = newLevel;
    }

    @Override
    public int costOfFarm(String farmType) {
        return getFarmCost(farmType);
    }

    @Override
    public int costOfUpgrade(String farmType) {
        return getUpgradeCost(farmType);
    }

    @Override
    public Farm farmElement() {
        return null;
    }
}
