package com.justin.Entities;

import com.justin.Interfaces.FarmInterface;
import com.justin.Interfaces.PlayerInfo;
import com.justin.Data.MySQL;
import com.justin.Utilities.Display;

import java.util.ArrayList;
import java.util.List;

public class Base implements FarmInterface {
    private List<Farm> base;
    private MySQL sql;
    private PlayerInfo player;

    public Base(boolean newPlayer, PlayerInfo player, MySQL sql) {
        base = new ArrayList<>();
        this.player = player;
        this.sql = sql;
        if (!newPlayer) {
            for (int location = 0; location < sql.getFarms().size(); location++) {
                base.add(new Farm(sql.getFarms().get(location), location, sql.getFarmLevels().get(location)));
            }
        }
    }

    public double getProfit() {
        double profit = 0;
            for (int i = 0; i < base.size(); i ++) {
                profit += base.get(i).calculateFarmProfit(base.get(i).getFarmType(), base.get(i).getFarmLevel());
            }
            Display.showProfits(profit);
        return profit;
    }

    public void buyFarm(String farmType) throws Exception {
        double money = player.getMoney();
        int slot = getEmptyFarmSlot();
        int cost = costOfFarm(farmType);
        if (slot < 5) {
            if (money >= cost) {
                player.setMoney(money - cost);
                base.add(new Farm(farmType, slot));
                Display.farmBought();
                sql.saveFarmData("insert", player, farmType, Integer.toString(slot + 1));
            } else {
                Display.notEnoughMoney();
            }
        } else {
            System.out.println("Your base is full!");
        }
    }

    public void sellFarm(String farmSlot) throws Exception {
        String farmType = base.get(Integer.parseInt(farmSlot) - 1).getFarmType();
        double money = player.getMoney();
        int cost = costOfFarm(farmType);
        int slot = (Integer.parseInt(farmSlot)) - 1;
        if (farmType != null) {
            player.setMoney(money + cost);
            resetFarmSlot(slot);
            Display.farmSold();
            base.remove(slot);
            sql.saveFarmData("delete", player, farmType, farmSlot);
        } else {
            System.out.println("Farm not found! Syntax: sell <farmType> <farmSlot>");
        }
    }

    public void upgradeFarm(String farmSlot) throws Exception {
        try {
            int slot = (Integer.parseInt(farmSlot)) - 1;
            String farmType = base.get(slot).getFarmType();
            int upgradeCost = costOfUpgrade(farmType);
            int currentLevel = player.getBase().getBase().get(slot).getFarmLevel();
            double money = player.getMoney();

            if (farmType != null) {
                if (money >= upgradeCost) {
                    player.setMoney(money - upgradeCost);
                    base.set(slot, player.getBase().getBase().get(slot)).setFarmLevel(currentLevel + 1);
                    Display.farmUpgraded();
                    sql.saveFarmData("update", player, farmType, farmSlot);
                } else {
                    Display.notEnoughMoney();
                }
            } else {
                System.out.println("Farm not found! Syntax: upgrade <farmSlot>");
            }
        } catch (Exception e) {
            System.out.println("Upgrade Farm Error: " + e);
        }
    }

    public void showBase() {
        int baseSize = player.getBase().getBase().size();
        System.out.print("[");
        for (int i = 0; i < baseSize; i++) {
            System.out.print(base.get(i).getFarmType() + base.get(i).getFarmLevel());
            if (baseSize > 1 && i < baseSize - 1) {
                System.out.print(", ");
            } else {
                System.out.println("]");
            }
        }
        System.out.print("\n");
        System.out.println(base);
    }


    public List<Farm> getBase() {
        return base;
    }
    private void resetFarmSlot(int slot) {
        base.set(slot, player.getBase().farmElement()).setFarmType("-");
            base.get(slot).cancelTimer();
    }

    private int getEmptyFarmSlot() {
        int emptySlot = 5;

            if (getBase().size() < 4) {
                emptySlot = getBase().size();
            }
        return emptySlot;
    }

    @Override
    public int costOfFarm(String farmType) {
        Farm farm = new Farm();
        return farm.getFarmCost(farmType);
    }

    @Override
    public int costOfUpgrade(String farmType) {
        Farm farm = new Farm();
        return farm.getUpgradeCost(farmType);
    }

    @Override
    public Farm farmElement() {
        return new Farm();
    }
}
