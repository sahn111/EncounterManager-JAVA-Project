package com.company.models;
import com.company.game_service.EncounterManager;

import java.lang.Thread;
/**
 * This class implements PotencyCalculator interface, and it will be used as enemy in the game.
 * We use protected field for class and control them with getters and setters in case of any change.
 * With the constructor of class, user is able to create her/his enemy easily.
 * Since class is implemented the PotencyCalculator interface, we had to override those functions, and completed them.
 * */
public class Enemy extends Thread implements PotencyCalculator{
    protected int entityID;
    protected int healthPoints;
    protected int baseDamage;
    protected final EncounterManager encounterManager;

    protected static int counter = 1; //counter for enemy,
    // with this counter we will be able to control 4th attack of enemy.

    /**
     * Constructor for enemy class
     */
    public Enemy(
            int entityID,
            int healthPoints,
            int baseDamage,
            EncounterManager encounterManager
    )
    {
        this.entityID = entityID;
        this.healthPoints = healthPoints;
        this.baseDamage = baseDamage;
        this.encounterManager = encounterManager;
    }

    /**
     * This function will be active when enemy is called by start() funtion.
     * Enemy will attack to tank everyy second. If it is enemy's 4th attack
     * it will operate group wide attack.
     * */
    @Override
    public void run() {
        while(encounterManager.playersAreAlive() && encounterManager.enemyIsAlive()) {
            if (counter % 5 == 0 )
                encounterManager.groupWideAttack();
            else
                encounterManager.enemyAttack("t");

            encounterManager.printHealth();
            counter++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * We will override these functions since we implemented PotencyCalculator interface
     */
    @Override
    public int dealDamage() {
        return this.getBaseDamage();
    }

    /**
     * When someone attacked to enemy, this function will be used for taking the damage.
     * */
    @Override
    public void takeDamage(int damage) {
        int health = this.getHealthPoints();
        health = health - damage;
        this.setHealthPoints(Math.max(health, 0));
    }

    /**
     * set counter variable
     */
    public void setCounter(int counter) {
        Enemy.counter = counter;
    }

    /**
     * get enetityID
     * */
    public int getEntityID() {
        return entityID;
    }

    /**
     * set entityID
     * */
    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    /**
     * get health point of enemy
     * */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * set health point of enemy
     * */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * get baseDamage of enemy
     * */
    public int getBaseDamage() {
        return baseDamage;
    }

    /**
     * set baseDamage of the enemy
     * */
    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
}
