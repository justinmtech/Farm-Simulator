package com.justin.Interfaces;

import com.justin.Entities.Farm;

public interface FarmInterface {
    int costOfFarm(String farmType);
    int costOfUpgrade(String farmType);
    Farm farmElement();
}
