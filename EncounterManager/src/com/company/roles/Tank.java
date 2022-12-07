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
public class Tank extends Player {
    protected int defense;
    protected final EncounterManager encounterManager;
    public Tank(String role, int entityID, int healthPoints, int baseDamage, int defense, EncounterManager encounterManager) {
        super(role, entityID, healthPoints, baseDamage);// We extended Player abstract class so that we use super() to use its constructor
        this.defense = defense;
        this.encounterManager = encounterManager;
    }

    /**
     * This is thread function, when Tank called by using start(), this function will be used.
     * Tank will attack to enemy every second with this function.
     * */
    @Override
    public void run() {
        while(encounterManager.playersAreAlive() && encounterManager.enemyIsAlive()) {
            encounterManager.playerAttack("t");
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
     */
    @Override
    public int dealDamage() {
        if(this.alivecheck()) {
            return super.getBaseDamage();
        }
        else{
            System.out.println("Tank is dead");
            return 0;
        }
    }

    /**
     * For tank class, takeDamage function operates differently from others. We apply defense speciality to this function.
     */
    @Override
    public void takeDamage(int damage) {
        if(this.alivecheck()) {
            if (damage > this.defense) {
                damage = damage - this.defense;
                healthPoints = super.getHealthPoints();
                healthPoints = healthPoints - damage;
                super.setHealthPoints(Math.max(healthPoints, 0));
            }
        }
        else{
            System.out.println("Tank is dead");
        }
    }

    /**
     * Check if object is alive or not, which can be done by checking health point.
     * It should be more than zero.
     * */
    public boolean alivecheck(){
        return super.getHealthPoints() > 0;
    }

    /**
     * get defense value of the tank
     */
    public int getDefense() {
        return defense;
    }

    /**
     * set defense value of the tank
     */
    public void setDefense(int defense) {
        this.defense = defense;
    }
}
