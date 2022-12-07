package com.company.models;
/**
 * This is an interface, we have 2 different function here, and these functions will be used by all of other classes.
 * By using interface we decrement our code complexity a lot.
* */
public interface PotencyCalculator {
    public int dealDamage();
    public void takeDamage(int damage);
}
