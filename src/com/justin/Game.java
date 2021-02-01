package com.justin;

import com.justin.Data.MySQL;
import com.justin.Entities.Player;
import com.justin.Interfaces.PlayerInfo;
import com.justin.Utilities.CommandHandler;

import java.util.Scanner;

public class Game {
    private MySQL sql;
    private String username;
    private boolean running;
    private Scanner scan;
    private PlayerInfo player;
    private CommandHandler commandHandler;

    public Game() {
        running = true;
        this.sql = new MySQL();
        this.scan = new Scanner(System.in);
    }

    void start() throws Exception {
        System.out.println("Enter username:");
        username = scan.next();
        loadData();
        loop();
    }

    public void stop() {
        running = false;
    }

    void loop() throws Exception {
        commandHandler = new CommandHandler(this, new Scanner(System.in), player);
        while (running) commandHandler.getCommand();
        System.out.println("Game closed..");
    }

    void loadData() throws Exception {
        if (sql.userExists(username)) {
            sql.loadPlayerData(username);
            sql.loadFarmData(username);
            player = new Player(false, username, sql);
        } else {
            player = new Player(true, 10000, username, sql);
            sql.createPlayerData(player);
        }
        System.out.println(player);
    }
}
