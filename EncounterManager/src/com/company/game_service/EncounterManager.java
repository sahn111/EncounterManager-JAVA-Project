package com.company.game_service;

import com.company.models.Enemy;
import com.company.roles.DamageDealer;
import com.company.roles.Healer;
import com.company.roles.Tank;

import java.util.Arrays;
import java.util.Scanner;
/**
 * General Information About Class:
 * Here we have exact 3 type of role and 1 enemy
 * Because we know that there can be 3 player with unique roles
 * For this class, we do not need to have constructor.
 * We play game with this class, therefore; all other classes are used in this class.
 * There is some global and local control values. For example, registiration_control is global one
 * but game_menu_option is local control value. Also, I added two new function to this class to make
 * it better, which are printHealth() and printGameMenu() these helps me to decrease the complexity of the code.
 * */
public class EncounterManager {
    private static EncounterManager singleton = null;
    protected Tank tank;
    protected DamageDealer damageDealer;
    protected Healer healer;
    protected Enemy enemy;
    protected int registration_control = 0; // if user create its owm players other than default, this will be one and user will use her/his own playersç
    // entity_id will help us to decide id of the entities easily.
    protected static int enemyDeadCounter = 0; // this counter prevent the printing "enemy is dead" more than one time
    protected int entity_id = 0;
    /**
     * This function make our EncounterManager singleton
     * which means that there will be only one EncounterManager always.
     * This function will ensure of it.
     * */
    public static EncounterManager getInstance(){
        if (singleton == null)
            singleton = new EncounterManager();

        return singleton;
    }

    /**
     * this function create new Tank object for EncounterManager class
     */
    public void registerTank(int healthpoints, int baseDamage , int defense){
        this.tank = new Tank("Tank", this.entity_id, healthpoints, baseDamage, defense, this);
        this.entity_id += 1;
    }

    /**
     * this function create new DamageDealer object for EncounterManager class
     */
    public void registerDamageDealer(int healthpoints, int baseDamage , int intelligence){

        this.damageDealer = new DamageDealer("DamageDealer", entity_id,healthpoints, baseDamage, intelligence, this);
        this.entity_id += 1;
    }

    /**
     * this function create new Healer object for EncounterManager class
     */
    public void registerHealer(int healthpoints, int baseDamage , int mind){

        this.healer = new Healer("Healer", entity_id, healthpoints, baseDamage, mind, this);
        this.entity_id += 1;
    }

    /**
     * this function create new Enemy object for EncounterManager class
     */
    public void spawnEnemy(int healthpoints, int baseDamage){
        this.enemy = new Enemy(entity_id, healthpoints, baseDamage, this);
        this.entity_id += 1;
    }

    /** While using menu() function we print health of roles each time,
     * then I decided to create function for that job.
     */
    public synchronized void printHealth(){
        System.out.println(
                "======================\n" +
                        "Entities' HP\n" +
                        "Tank : " + this.tank.getHealthPoints() +
                        "\n" +
                        "Damage Dealer : " + this.damageDealer.getHealthPoints() +
                        "\n" +
                        "Healer : " + this.healer.getHealthPoints() +
                        "\n" +
                        "Enemy : " + this.enemy.getHealthPoints() +
                        "\n======================\n");
    }

    /**
     * This function will check whether enemy's health is more than 0 or not.
     */
    public boolean enemyIsAlive(){
        if (this.enemy.getHealthPoints() > 0){
            return true;
        }
        else{
            if(enemyDeadCounter == 0)
                System.out.println("The enemy is dead. The encounter has ended.");

            enemyDeadCounter ++;
            return false;
        }
    }

    /**
     * While playing game, we should know that our players are alive or not, this function will give that information.
     */
    public boolean playersAreAlive(){
        if (tank.getHealthPoints() > 0 | damageDealer.getHealthPoints() > 0 | healer.getHealthPoints() > 0) {
            return true;
        }
        else{
            System.out.println("All players are dead");
            return false;
        }
    }

    /**
     *Enemy makes group wide attack to players with that function.
     */
    public synchronized void groupWideAttack(){
        int enemyDamage = this.enemy.dealDamage();
        this.tank.takeDamage(enemyDamage);
        this.damageDealer.takeDamage(enemyDamage);
        this.healer.takeDamage(enemyDamage);
        System.out.println("Enemy attacked all players (" +
                enemyDamage +
                ") damage attack");
    }

    /**
     *Players attack to enemy with that function
     *You select player to attack enemy than attack is done.
     */
    public synchronized void playerAttack(String option){
        int damage = 0;

        switch (option) {
            case "t":
                damage = this.tank.dealDamage();
                System.out.println("Tank attacked the enemy (" + damage + " damage attack)");
                break;
            case "d":
                damage = this.damageDealer.dealDamage();
                System.out.println("Damage dealer attacked the enemy (" + damage + "damage attack)");
                break;
            case "h":
                damage = this.healer.dealDamage();
                System.out.println("Healer attacked the enemy (" + damage + "damage attack)");
                break;
        }
        this.enemy.takeDamage(damage);
    }

    /**
     * Enemy attack to one of the players with that function.
     */
    public synchronized void enemyAttack(String option){
        int enemyDamage = this.enemy.getBaseDamage();

        switch (option) {
            case "t":
                this.tank.takeDamage(enemyDamage);
                System.out.println("The tank was attacked (" + enemyDamage + "damage attack)");
                break;
            case "d":
                this.damageDealer.takeDamage(enemyDamage);
                System.out.println("The damage dealer was attacked (" + enemyDamage + "damage attack)");
                break;
            case "h":
                this.healer.takeDamage(enemyDamage);
                System.out.println("The healer was attacked (" + enemyDamage + "damage attack)");
                break;
        }
    }

    /**
     * As we all know that we have an healer role. By using this function,
     * our healer heal one of the players.
     */
    public synchronized void healPlayer(String option){

        switch (option) {
            case "t":
                int tankHeal = this.tank.getHealthPoints();
                tankHeal = tankHeal + this.healer.heal();
                this.tank.setHealthPoints(Math.min(tankHeal, 100));
                System.out.println("The tank was healed by " + this.healer.heal() + "HP");
                break;
            case "d":
                int damageDealerHeal = this.damageDealer.getHealthPoints();
                damageDealerHeal = damageDealerHeal + this.healer.heal();
                this.damageDealer.setHealthPoints(Math.min(damageDealerHeal, 100));
                System.out.println("The damage dealer was healed by " + this.healer.heal() + "HP");
                break;
            case "h":
                int healerHeal = this.healer.getHealthPoints();
                healerHeal = healerHeal + this.healer.heal();
                this.healer.setHealthPoints(Math.min(healerHeal, 100));
                System.out.println("The healer was healed by " + this.healer.heal() + "HP");
                break;
        }
    }

    /**
     * This function will compare health of players and will return the
     * player with lowest health
     * */
    public synchronized String lowestHealth(){
        String lowest = null;
        int tankHealth = this.tank.getHealthPoints();
        int damageDealerHealth = this.damageDealer.getHealthPoints();
        int healerHealth = this.healer.getHealthPoints();

        if (tankHealth < damageDealerHealth){
            if (tankHealth < healerHealth){
                lowest = "t";
            }
            else {
                lowest = "h";
            }
        }
        else{
            if (damageDealerHealth < healerHealth){
                lowest = "d";
            }
            else{
                lowest = "h";
            }
        }
        return lowest;
    }

    /**
     * With this function we use menus and play the game.
     */
    public void menu(){
        Scanner sc= new Scanner(System.in); // will be used in input operation
        String[] roles; // wil be used in remainder part
        String game_menu_option;
        String role_option;
        int arr_counter;
        int stop_encounter;
        while(true) {
            roles = new String[4];
            arr_counter = 0;
            stop_encounter = 0;

            System.out.println(
                    "1) Register entities\n" +
                            "2) Start encounter\n" +
                            "3) Exit"
            );
            int option = sc.nextInt();

            if (option == 3) {
                //Exit.
                System.out.println("Good Bye!");
                return;
            }
            else if (option == 1) {
                this.registration_control = 1;
                //Register Entities.
                int counter = 0;
                String register_option;
                int healthPoint;
                int baseDamage;
                int specialAbility;

                //here we need to create four different type of role
                //so that this loop will iterate 4 times
                while (counter < 4) {
                    // here, we help player to remember roles that she/he created
                    System.out.println("Role(s) that you created : ");
                    System.out.println(Arrays.toString(roles));
                    System.out.println("--------------------------");
                    System.out.println("Select an entity to register:\n" +
                            "a) Tank\n" +
                            "b) Damage Dealer\n" +
                            "c) Healer\n" +
                            "d) Enemy\n" +
                            "x) Exit");

                    register_option = sc.next();
                    /*
                     * Here we help user to create unique team with 3 player and 1 enemy.
                     * Since we do same operation with different ways, I wil explain them in once
                     * First we want user to select role that she/he wants to create, then check if user created that role or not
                     * then we take inputs for that role and create one for user to use it.
                     * */
                    switch (register_option) {
                        case "x":
                            this.registration_control = 0;
                            counter = 5;
                            break;
                        case "a":
                            if (Arrays.stream(roles).anyMatch("Tank"::equals)) {
                                System.out.println("You have one tank player in team");
                                counter -= 1;
                                break;
                            }
                            System.out.println("Enter health point :");
                            healthPoint = sc.nextInt();
                            System.out.println("Enter base damage :");
                            baseDamage = sc.nextInt();
                            System.out.println("Enter defense :");
                            specialAbility = sc.nextInt();
                            this.registerTank(healthPoint, baseDamage, specialAbility);
                            roles[arr_counter] = "Tank";
                            arr_counter = arr_counter + 1;
                            break;
                        case "b":
                            if (Arrays.stream(roles).anyMatch("Damage Dealer"::equals)) {
                                System.out.println("You have one damage dealer player in team");
                                counter -= 1;
                                break;
                            }
                            System.out.println("Enter health point :");
                            healthPoint = sc.nextInt();
                            System.out.println("Enter base damage :");
                            baseDamage = sc.nextInt();
                            System.out.println("Enter intelligence :");
                            specialAbility = sc.nextInt();
                            this.registerDamageDealer(healthPoint, baseDamage, specialAbility);
                            roles[arr_counter] = "Damage Dealer";
                            arr_counter = arr_counter + 1;
                            break;
                        case "c":
                            if (Arrays.asList(roles).contains("Healer")) {
                                System.out.println("You have one healer player in team");
                                counter -= 1;
                                break;
                            }
                            System.out.println("Enter health point :");
                            healthPoint = sc.nextInt();
                            System.out.println("Enter base damage :");
                            baseDamage = sc.nextInt();
                            System.out.println("Enter mind :");
                            specialAbility = sc.nextInt();
                            this.registerHealer(healthPoint, baseDamage, specialAbility);
                            roles[arr_counter] = "Healer";
                            arr_counter = arr_counter + 1;
                            break;
                        case "d":
                            if (Arrays.stream(roles).anyMatch("Enemy"::equals)) {
                                System.out.println("You have enemy already");
                                counter -= 1;
                                break;
                            }
                            System.out.println("Enter health point :");
                            healthPoint = sc.nextInt();
                            System.out.println("Enter base damage :");
                            baseDamage = sc.nextInt();
                            this.spawnEnemy(healthPoint, baseDamage);
                            roles[arr_counter] = "Enemy";
                            arr_counter = arr_counter + 1;
                            break;
                        default:
                            System.out.println("Wrong input!");
                            counter = counter - 1;
                    }
                    counter = counter + 1;
                }
            }
            else if (option == 2) {
                /**
                 *   Start game.
                 *
                 *   Attention !
                 *   If you start game directly, you will use default values :
                 *
                 *      Tank -> health : 100, damage : 10, defense : 6
                 *      Damage Dealer -> health : 100, damage : 10, intelligence : 7
                 *      Healer -> health : 100, damage : 10, mind : 8
                 *      Enemy -> health : 100, damage : 10
                 */
                if (this.registration_control != 1) {
                    // when user did not create her/his own players
                    //As you requested, I created default characters for you to test easily hocam
                    this.registerTank(100, 10, 6);
                    this.registerDamageDealer(100, 10, 7);
                    this.registerHealer(100, 10, 8);
                    this.spawnEnemy(100, 10);
                }
                /*
                 * Here we just call threads to run and do the required operations
                 * after they finished we set "enemy counter" to zero again and prepare it to the next iteration.
                 * */
                enemy.setCounter(1);
                tank.start();
                damageDealer.start();
                healer.start();
                enemy.start();
                try {
                    tank.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    damageDealer.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    healer.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    enemy.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                enemyDeadCounter = 0; //we make EnemyDeadCounter zero again
            }
        }
    }

}
