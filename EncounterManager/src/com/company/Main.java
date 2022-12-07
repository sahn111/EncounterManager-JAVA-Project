package com.company;

import com.company.game_service.EncounterManager;
/**
 * This is our main class, we create our EncounterManager here
 * then I call menu() function to start game. Finally game starts..
 * */
public class Main {

    public static void main(String[] args) {
        // Create EncounterManager object as singleton
        EncounterManager em = EncounterManager.getInstance();
        //use EncounterManager object
        em.menu();
    }
}
