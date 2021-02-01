package com.justin.Utilities;

import com.justin.*;
import com.justin.Interfaces.PlayerInfo;

import java.util.Scanner;

public class CommandHandler {
    private Game game;
    private PlayerInfo player;
    private String[] inputs;
    private Scanner scan;

    public CommandHandler(Game game, Scanner scan, PlayerInfo player) {
        this.game = game;
        this.scan = scan;
        this.player = player;
        System.out.println("Listener: " + player);
    }

    public void getCommand() throws Exception {
            Display.enterCommand();
            String input = scan.nextLine().toUpperCase();
            inputs = input.split(" ", 2);
            execute();
        }

    private void execute() throws Exception {
        try {
            if (inputs[0].equals("QUIT")) {
                game.stop();
            } else if (inputs[0].equals("BASE")) {
                player.getBase().showBase();
            } else if (inputs[0].equals("FARM")) {
                player.setMoney(player.getBase().getProfit());
            } else if (inputs[0].equals("MONEY")) {
                System.out.println(player.getMoney());
            } else if (inputs[0].equals("SELL")) {
                player.getBase().sellFarm(inputs[1]);
            } else if (compareInputs(inputs[0], "BUY", inputs[1], "C", "S", "G", "D")) {
                player.getBase().buyFarm(inputs[1]);
            } else if (compareInputs(inputs[0], "UPGRADE", inputs[1], "1", "2", "3", "4")) {
                player.getBase().upgradeFarm(inputs[1]);
            } else {
                Display.invalidCommand();
            }
        } catch (Exception e) {
            System.out.println("Listener error!: " + e);
        }
    }

    private boolean compareInputs(String input, String cmd, String input2, String arg, String arg2, String arg3, String arg4) {
        boolean inputsCompared = false;

        if (input.equalsIgnoreCase(cmd) && input2.equalsIgnoreCase(arg) ||
        input.equalsIgnoreCase(cmd) && input2.equalsIgnoreCase(arg2) ||
        input.equalsIgnoreCase(cmd) && input2.equalsIgnoreCase(arg3) ||
        input.equalsIgnoreCase(cmd) && input2.equalsIgnoreCase(arg4)) {
            inputsCompared = true;
        }
        return inputsCompared;
    }
}
