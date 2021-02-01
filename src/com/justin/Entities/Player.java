package com.justin.Entities;

import com.justin.Interfaces.PlayerInfo;
import com.justin.Data.MySQL;

public class Player implements PlayerInfo {
    private double money;
    private Base base;
    private String username;
    private MySQL sql;

    public Player(boolean newPlayer, double money, String username, MySQL sql) {
        this.money = money;
        this.username = username;
        this.sql = sql;
        this.base = new Base(newPlayer, this, sql);
        System.out.println("Base Instantiated (Player Constructor1): " + base);
    }

    public Player(boolean newPlayer, String username, MySQL sql) {
        this.money = sql.getMoney();
        this.username = username;
        this.sql = sql;
        this.base = new Base(newPlayer, this, sql);
        System.out.println("Base Instantiated (Player Constructor2): " + base);
    }

    @Override
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public double getMoney() {
        return money;
    }

    @Override
    public Base getBase() {
        return base;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
