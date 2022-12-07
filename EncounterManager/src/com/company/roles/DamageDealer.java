package com.company.roles;

import com.company.game_service.EncounterManager;
import com.company.models.Player;

/**
 * In every role class we have special ability.
 * Moreover, in every operation we use alivecheck() function to check if it is alive or not
 * Also, we have constructor to use in creation operation
 * As you know, there are different types of special ability and each of them implemented different.
 * I used protected protected values for special abilities of role classes, and controlled them with
 * getter and setters. Since I extended Player abstract class, used "super" to reach its values.
 * */
public class DamageDealer extends Player {
    protected int intelligence;
    protected final EncounterManager encounterManager;
    /**
     * Constructor for the DamageDealer class
     */
    public DamageDealer(String role, int entityID, int healthPoints, int baseDamage, int intelligence, EncounterManager encounterManager) {
        super(role, entityID, healthPoints, baseDamage); // We extended Player abstract class so that we use super() to use its constructor
        this.intelligence = intelligence;
        this.encounterManager = encounterManager;
    }
    /**
     * This is thread operation of DamageDealer. Damage dealer will attack to enemy
     * every 0.5 second.
     * */
    @Override
    public void run(){
        while(encounterManager.playersAreAlive() && encounterManager.enemyIsAlive()) {
            encounterManager.playerAttack("d");
            encounterManager.printHealth();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * We have to override functions of PotencyCalculator interface
     *Speciality of the Damage Dealer is giving more damage, and we implemented that speciality here.
     */
    @Override
    public int dealDamage(){
        if (this.alivecheck()) {
            int damage = super.getBaseDamage();
            damage = damage + this.intelligence;
            return damage;
        }
        else{
            System.out.println("DamageDealer is dead");
            return 0;
        }
    }
    /**
     * This class will take damage by using this function.
     * */
    @Override
    public void takeDamage(int damage) {
        if (alivecheck()) {
            healthPoints = super.getHealthPoints();
            healthPoints = healthPoints - damage;
            super.setHealthPoints(Math.max(healthPoints, 0));
        }
        else{
            System.out.println("DamageDealer is dead");
        }
    }
    /**
     * This function will check if object is alive or not.
     * */
    public boolean alivecheck(){
        return super.getHealthPoints() > 0;
    }

    /**
     * get intelligence of the damage dealer
     */
    public int getIntelligence() {
        return intelligence;
    }
    /**
     * set intelligence of the damage dealer
     */
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
}
