package world;

import characters.Player;

public class TutorialInstance {

    public static void doTutorial(Player player) {
        TutorialInstance tutorial = new TutorialInstance();
        tutorial.introDialogue();


    }

    void introDialogue() {
        String[] dialogLines = {"Welcome! To an un-named land (because the creator was too lazy)!"
                , "You are a young villager who is set out to become an adventurer and make a name for yourself."
                , "Today is the day of enrollment! Can you pass the guild's test and start your journey?"};
        for (String line : dialogLines) {
            System.out.println(line);
            try {Thread.sleep(400);} catch (InterruptedException e) {
                System.out.println("System error while in dialogue. Closing game...");
                System.exit(1);
            }
        }
    }

}
