package com.justin.Data;

import com.justin.Interfaces.PlayerInfo;

import java.sql.*;
import java.util.ArrayList;

public class MySQL {
    private Connection conn;
    private PreparedStatement pstat;
    private ResultSet rs;
    private Statement stmt;
    private double money;
    private ArrayList<String> farms;
    private ArrayList<Integer> farmLevels;

    private Connection connect() throws Exception {
        try {
            String url = "jdbc:mysql://localhost:3306/farm_game_database";
            String user = "root";
            String password = "41oq4ptJeBV4o1";
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean userExists(String username) throws Exception {
            boolean exists = false;
        try {
            connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT EXISTS (SELECT username FROM players WHERE username = '" + username + "') AS existCheck");
            while (rs.next()) {
                int existCheck = rs.getInt("existCheck");
                if (existCheck == 1) {
                    exists = true;
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return exists;
    }

    public void createPlayerData(PlayerInfo player) throws Exception {
        try {
            connect();
            pstat = conn.prepareStatement("INSERT INTO players VALUES (?, ?)");
            pstat.setString(1, player.getUsername());
            pstat.setString(2, Double.toString(player.getMoney()));
            pstat.execute();
        } catch(Exception e) {
            System.out.println(e);
        }
    }


    public void savePlayerData(PlayerInfo player) throws Exception {
        try {
            connect();
            pstat = conn.prepareStatement("UPDATE players SET player_money = ? WHERE username = ?");
            pstat.setString(1, Double.toString(player.getMoney()));
            pstat.setString(2, player.getUsername());
            pstat.execute();

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void loadPlayerData(String username) throws Exception {
        try {
            connect();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM players WHERE username = " + "'" + username + "'");
            while (rs.next()) {
                money = rs.getInt("player_money");
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void saveFarmData(String type, PlayerInfo player, String farmType, String farmLocation) throws Exception {
        try {
            connect();
            if (type == "update") {
                pstat = conn.prepareStatement("UPDATE farms SET farm_level = farm_level + 1 WHERE owner_username = ? AND farm_location = ?");
                pstat.setString(1, player.getUsername());
                pstat.setString(2, farmLocation);
                pstat.execute();
            }
            else if (type == "insert") {
                pstat = conn.prepareStatement("INSERT INTO farms VALUES (?, ?, DEFAULT, ?)");
                pstat.setString(1, player.getUsername());
                pstat.setString(2, farmType);
                pstat.setString(3, farmLocation);

                pstat.execute();
            }
            else if (type == "delete") {
                pstat = conn.prepareStatement("DELETE FROM farms WHERE owner_username = ? AND farm_location = ?");
                pstat.setString(1, player.getUsername());
                pstat.setString(2, farmLocation);
                pstat.execute();
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

//NEEDS FIXING
//    public void loadFarmData(String username) throws Exception {
//        farms = new ArrayList<>();
//        farmLevels = new ArrayList<>();
//        try {
//            connect();
//            stmt = conn.createStatement();
//            rs = stmt.executeQuery("SELECT * FROM farms WHERE owner_username = " + "'" + username + "'");
//            while (rs.next()) {
//                String farmType = rs.getString("farm_type");
//                int farmLevel = rs.getInt("farm_level");
//                farms.add(farmType);
//                farmLevels.add(farmLevel);
//            }
//        } catch(Exception e) {
//            System.out.println(e);
//        }
//    }

    public ArrayList<String> getFarms() {
        return this.farms;
    }
    public ArrayList<Integer> getFarmLevels() {
        return this.farmLevels;
    }
    public double getMoney() {
        return money;
    }
}
