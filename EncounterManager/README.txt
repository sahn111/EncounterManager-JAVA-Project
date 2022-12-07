Please read this file at first.

GENERAL INFORMATION :

    * In this project you will play a game without GUI.
    * You can either create your own special characters or you can use default characters which are given by program.

HOW TO START :

    * You can access game from your terminal(cmd in windows) by going to file which EncounterManager/out/artifacts/EncounterManager_jar.
        **Use "java -jar EncounterManager.jar" command to start game.

RULES :

    * There must be 4 unique type of role in game. (Tank, Damage Dealer, Healer, Enemy)
    * Roles can not use their abilities if they are dead.
    * If all roles or enemy dies, game will be completed.

HOW TO PLAY :

    * In main menu, if you press (1) you will be able to create your roles
    * In main menu, if press (2) you will be able to start game. If you did not create special characters, you will play with default ones.
        ** Since we are using threads, game will be played by itself automatically.
    * Press (3) to exit

COMPILATION :

    * You will use EncounterManager class to play game.
    * EncounterManager class is singleton so that user not be able to has two different EncounterManager class.
    * EncounterManager class has all type of players; for instance, enemy, tank etc.
    * When you select "start encounter" option, game will be played automatically.
    * We will use "run()" methods of classes which are extends Thread.
    * In their "run()" methods they will perform until all players are dead or enemy is dead.
    * There will be some rules for them to obey while processing. For example, they need to sleep() some amount of time.
    * When threads completed, user will be able to use game menu again.

HOW TO REACH DOCUMENT :

    * You can access JavaDoc document.
    * In JavaDoc you will be able to see all the information about all methods in project.