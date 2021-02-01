package com.justin.Utilities;

import java.text.NumberFormat;

public class Display {
    public static NumberFormat format = NumberFormat.getCurrencyInstance();

    public static void enterCommand() {
        System.out.println("Enter command: ");
    }

    public static void invalidCommand() {
        System.out.println("Invalid command!");
    }

    public static void showProfits(double profit) {
        System.out.println("Farm Profits: " + format.format(profit));
    }

    public static void showMoney(double money) {
        System.out.println("Total Money: " + format.format(money));
    }

    public static void farmUpgraded() {
        System.out.println("Farm upgraded!");
    }

    public static void farmSold() {
        System.out.println("Farm sold!");
    }

    public static void farmBought() {
        System.out.println("Farm bought!");
    }

    public static void notEnoughMoney() {
        System.out.println("Not enough money!");
    }

}
