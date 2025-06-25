import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Blackjack {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static double bal = 100;
    static double winnings;
    static String playerChoice = "";
    static ArrayList<Cards> playerHand = new ArrayList<>();
    static ArrayList<Cards> dealerHand = new ArrayList<>();
    static boolean isSplit = false;
    static boolean surrendered;
    static Blackjack game = new Blackjack();


    public static void main(String[] args) throws InterruptedException {

        double bet;
        String keepPlaying = "";

        System.out.println("*********************");
        System.out.println("Welcome to Blackjack!");
        System.out.println("*********************\n");

        Thread.sleep(2000); //delay b/t welcome message and first bet placement

        do {
            boolean[] outcome = new boolean[3];
            surrendered = false;
            if(!playerHand.isEmpty()){
                playerHand.clear();
                dealerHand.clear();
            }
            System.out.print("Enter your bet: ");
            bet = scanner.nextDouble();
            scanner.nextLine();
            if(bet <= 0) {
                System.out.println("Invalid Bet\n");
                continue;
            } else if(bet > bal) {
                System.out.println("Insufficient Funds\n");
                continue;
            } else {
                bal -= bet;
                System.out.println("Bet placed!\n");
                Thread.sleep(250);
            }

            playerHand.addAll(drawCards(2));
            System.out.println("Your hand: ");
            game.checkHand(playerHand, 'p');
            dealerHand.addAll(drawCards(2));
            System.out.println("The dealer draws their cards..");
            Thread.sleep(500);
            System.out.print("The dealer shows you their first card: ");
            System.out.println(dealerHand.getFirst());
            if(game.checkHand(playerHand, 'd') == 21 || dealerHand.getFirst().cardValue == 10 || dealerHand.getFirst().cardValue == 11) {
                System.out.println("Potential blackjack detected, checking game outcome.");
                outcome = checkBlackjack(playerHand, dealerHand);
            }
            game.checkHand(dealerHand, 'd');
            if (playerHand.size() == 2 && (playerHand.getLast().toString().equals(playerHand.getFirst().toString()))){
                System.out.println("Your deck is eligible to split! Would you like to do this? (Y/N): ");
                if(scanner.nextLine().equalsIgnoreCase("Y")) {
                    isSplit = true;
                    playerHand.removeLast();
                    System.out.println("You have chosen to split!");
                }
            }
            if(isSplit){
                for(int i = 0; i < 2; i++) {
                    System.out.println("\nPlaying split deck #" + (i + 1));
                    outcome = playRound(playerHand, dealerHand, playerChoice, bet);
                    processWin(outcome, bet);
                }
            }
            else if(outcome[1]) {
                processWin(outcome, bet);
            }
            else {
                outcome = playRound(playerHand, dealerHand, playerChoice, bet);
                processWin(outcome, bet);
            }

            System.out.println("Your new balance is $" + bal);

            if(bal > 0){
                do{
                    System.out.print("\nDo you want to keep playing? (Y/N): ");
                    keepPlaying = scanner.nextLine().toUpperCase();
                    if(!keepPlaying.equals("Y") && !keepPlaying.equals("N")) {
                        System.out.println("Invalid response.");
                        keepPlaying = "";
                    }
                } while (keepPlaying.isEmpty());
            }


        } while (bal > 0 && keepPlaying.equals("Y"));

        System.out.println("Your final balance was $" + bal + ". Thanks for playing!");

        scanner.close();
    }

    // Draws cards when called, # of cards to draw is fed when called
    public static ArrayList<Cards> drawCards(int drawCards) {
        ArrayList<Cards> hand = new ArrayList<>();
        for(int i = 0; i < drawCards; i++) {
            String cardFace = "";
            int cardValue = random.nextInt(1, 15);
            switch(cardValue){
                case 1, 11 -> cardFace = "A";
                case 2 -> cardFace = "2";
                case 3 -> cardFace = "3";
                case 4 -> cardFace = "4";
                case 5 -> cardFace = "5";
                case 6 -> cardFace = "6";
                case 7 -> cardFace = "7";
                case 8 -> cardFace = "8";
                case 9 -> cardFace = "9";
                case 10 -> cardFace = "10";
                case 12 -> cardFace = "J";
                case 13 -> cardFace = "Q";
                case 14 -> cardFace = "K";
            }
            hand.add(new Cards(cardFace, cardValue));
        }
        return hand;
    }

    // Totals points for every card within the hand + checks and determines value for Aces within the hand if necessary
    public int checkHand(ArrayList<Cards> hand, char target) {
        int totalPoints = 0;
        int aceCount = 0;
        for(Cards card : hand) {
            totalPoints = totalPoints + card.cardValue;
            if (card.cardFace.equals("A")) {
                aceCount++;
            }
            if(target == 'p'){
                System.out.println(card);
            }
        }
        while(aceCount > 0 && totalPoints > 21) {
            totalPoints -= 10;
            aceCount--;
        }
        if(target == 'p'){
            System.out.println("Total points: " + totalPoints + "\n");
        }
        return totalPoints;
    }

    public static boolean[] checkBlackjack(ArrayList<Cards> playerHand, ArrayList<Cards> dealerHand) {
        boolean[] outcome = {false, true, false};
        int pointsPlayer = 0;
        int pointsDealer = 0;
        for(Cards card : playerHand) {
            pointsPlayer += card.cardValue;
        }
        for(Cards card : dealerHand) {
            pointsDealer += card.cardValue;
        }
        if(pointsPlayer == pointsDealer) {
            outcome[2] = true;
        }
        else if(pointsPlayer == 21) {
            outcome[0] = true;
        }
        else if(pointsDealer < 21) {
            outcome[1] = false;
            System.out.println("No blackjack detected, continuing game..");
        }

        return outcome;
    }

    public static String getPlayerChoice(double bet, ArrayList<Cards> playerHand) {
        do {
            System.out.println("\nOptions: hit, stand, double down, surrender, info");
            System.out.print("Enter the action you would like to take: ");
            playerChoice = scanner.nextLine().toLowerCase().trim();
            if(
                    !playerChoice.equals("hit") &&
                    !playerChoice.equals("stand") &&
                    !playerChoice.equals("double down") &&
                    !playerChoice.equals("surrender") &&
                    !playerChoice.equals("info")
            ){
                System.out.println("Invalid Choice!");
                playerChoice = "";
            }
            else {
                switch (playerChoice) {
                    case "hit" -> {
                        System.out.println("You have chosen to hit.");
                        playerHand.addAll(drawCards(1));
                    }
                    case "stand" -> {
                        System.out.println("You have chosen to stand.");
                        return playerChoice;
                    }
                    case "double down" -> {
                        if (bal - (bet * 2) >= 0) {
                            bal = bal - bet;
                            bet *= 2;
                            System.out.println("Bet increased to $" + bet);
                            System.out.println("Your new balance is: $" + bal);
                            playerHand.addAll(drawCards(1));
                            return playerChoice;
                        }
                    }
                    case "surrender" -> {
                        bet = bet / 2;
                        bal = bal + bet;
                        return playerChoice;
                    }
                    case "info" -> game.optionInfo();
                }
            }
        } while(playerChoice.isEmpty() || playerChoice.equals("info"));
        return playerChoice;
    }

    // prints info about choices when playerChoice is called and info is requested by player
    public void optionInfo() {
        System.out.println("""
                You have request Info on the options.
                1. Hit - draws one more card and adds to your total points.
                2. Stand - deny drawing more cards and end your turn.
                3. Double down - doubles your bet and take ONLY one more card. Must be done after initial draw.
                4. Surrender - Forfeits the round, but returns half of your bet to you.""");
    }


    public static boolean[] playRound(ArrayList<Cards> playerHand, ArrayList<Cards> dealerHand, String choice, double bet) {
        boolean[] outcome = {false, false, false}; // {win, blackjack, draw}
        boolean playerBust = false;
        boolean dealerBust = false;
        int totalPointsP = 0;
        int totalPointsD;

        // checks for

        // plays round
        do{
            choice = getPlayerChoice(bet, playerHand);
            if(choice.equals("surrender")) {
                System.out.println("You have chosen to surrender, half of your bet has been returned.");
                break;
            }
            totalPointsP = game.checkHand(playerHand, 'p');
            if(totalPointsP > 21) {
                playerBust = true;
                System.out.println("Your hand is over 21 points and has bust!\n");
            }
        } while (!choice.equals("stand") &&
                !playerBust);

        // finishes dealer hand and reports bust if true
        do{
            System.out.println("Dealer draws a card..");
            dealerHand.addAll(drawCards(1));
            if(game.checkHand(dealerHand, 'd') > 21) {
                dealerBust = true;
                System.out.println("The dealer busted!");
            }
        } while (game.checkHand(dealerHand, 'd') <= 16);

        // display dealer hand
        System.out.println("The dealer flips over their hand..");
        for(Cards card : dealerHand) {
            System.out.println(card);
        }
        totalPointsD = game.checkHand(dealerHand, 'd');
        System.out.println("Total points: " + totalPointsD + "\n");


        // outcome handler
        if(!playerBust){
            if(totalPointsP > totalPointsD || dealerBust) {
                outcome[0] = true;
            }
        }

        // DO-WHILE LOOP TO PROCESS THE playerChoice INPUT
        // PLAY DEALER SIDE
        // DETERMINE OUTCOME (CHECK BUST)

        return outcome;
    }

    // processes potential win outcomes and pays out to player
    public static void processWin(boolean[] outcome, double bet) {
        if(outcome[2]){
            bal += bet;
            System.out.println("The round was a draw! Your money was returned to you.");
        }
        else if (outcome[0]) {
           if (outcome[1]) {
               bal += (bet + (bet * 1.5));
               System.out.printf("Congrats! You won the round with a blackjack and earned $%.2f!\n", bet);
           }
           else {
               bal += (bet + bet);
               System.out.printf("Congrats! You earned $%.2f!\n", bet);
           }
        }
        else if (outcome[1]) {
            System.out.println("The dealer got a blackjack! You did not win any money.");
        }
        else {
            System.out.println("You did not win any money.");
        }
    }
}
