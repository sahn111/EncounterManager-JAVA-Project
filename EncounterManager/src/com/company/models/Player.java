package com.company.models;
/**
 * This class will be the abstract class of all type of roles.
 * Since they will use same values, we created this abstract class.
 * We also have constructor here, because I did not want to make my code more complex for reader.
 * When I use "super" in other classes which extends "Player", I can easily control Player class.
 * Again I used protected fields to improve safety of my program.
 * Moreover, I controlled protected fields with getters and setters.
 * */
public abstract class Player extends Thread implements PotencyCalculator {
    protected String role;
    protected int entityID;
    protected int healthPoints;
    protected int baseDamage;

    /**
     * Constructor for Player abstract class
     */
    protected Player(
            String role,
            int entityID,
            int healthPoints,
            int baseDamage
    )
    {
        this.role = role;
        this.entityID = entityID;
        this.healthPoints = healthPoints;
        this.baseDamage = baseDamage;
    }

    /**
     * get role of player
     */
    public String getRole() {
        return role;
    }

    /**
     * set the role of player
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * get entityID of player
     */
    public int getEntityID() {
        return entityID;
    }

    /**
     * set enetityID of player
     */
    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    /**
     * get healthPoints of player
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * set healthPoint of player
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * get BaseDamage of player
     */
    public int getBaseDamage() {
        return baseDamage;
    }

    /**
     * set BaseDamage of player
     */
    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }
}
