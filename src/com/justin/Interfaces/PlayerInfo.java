package com.justin.Interfaces;

import com.justin.Entities.Base;

public interface PlayerInfo {
    void setMoney(double money) throws Exception;

    double getMoney();

    Base getBase();

    String getUsername();
}
