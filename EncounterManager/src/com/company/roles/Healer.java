package com.company.roles;

import com.company.game_service.EncounterManager;
import com.company.models.Enemy;
import com.company.models.Player;
/**
 * In every role class we have special ability.
 * Moreover, in every operation we use alivecheck() function to check if it is alive or not
 * Also, we have constructor to use in creation operation
 * As you know, there are different types of special ability and each of them implemented different.
 * I used protected protected values for special abilities of role classes, and controlled them with
 * getter and setters. Since I extended Player abstract class, used "super" to reach its values.
 * */
public class Healer extends Player {
    protected int mind;
    protected final EncounterManager encounterManager;
    public Healer(String role, int entityID, int healthPoints, int baseDamage, int mind, EncounterManager encounterManager) {
        super(role, entityID, healthPoints, baseDamage);// We extended Player abstract class so that we use super() to use its constructor
        this.mind = mind;
        this.encounterManager = encounterManager;
    }

    /**
     * This is the thread part of this object. When Healer called by using start(), it will heal the player
     * which has the lowest health every second.
     */
    @Override
    public void run() {
        while(encounterManager.playersAreAlive() && encounterManager.enemyIsAlive()) {
            String target;
            target = encounterManager.lowestHealth();
            encounterManager.healPlayer(target);
            encounterManager.printHealth();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * We have to override functions of PotencyCalculator interface
     * */
    @Override
    public int dealDamage() {
        if (this.alivecheck()) {
            return super.getBaseDamage();
        }
        else{
            System.out.println("Healer is dead");
            return 0;
        }
    }

    /**
     * When someone attacked healer, we will use this function to lower healer health
     * */
    @Override
    public void takeDamage(int damage) {
        if (this.alivecheck()) {
            healthPoints = super.getHealthPoints();
            healthPoints = healthPoints - damage;
            super.setHealthPoints(Math.max(healthPoints, 0));
        }
        else{
            System.out.println("Healer is dead");
        }
    }

    /**
     * Check if healer is alive or not return true if healer is alive, return false otherwise.
     * */
    public boolean alivecheck(){
        return super.getHealthPoints() > 0;
    }

    /**
     * By using this function, you can heal another player.
     * Healing will increase their HP. This amount depends on
     * healer's "mind".
     * */
    public int heal() {
        if(this.alivecheck()) {
            return this.mind + 10;
        }
        else{
            System.out.println("Healer is dead!");
            return 0;
        }
    }

    /**
     * get mind variable of the healer
     */
    public int getMind() {
        return mind;
    }

    /**
     * set mind variable of the healer
     */
    public void setMind(int mind) {
        this.mind = mind;
    }
}
