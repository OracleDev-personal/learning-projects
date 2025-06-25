import java.util.Random;
import java.util.Scanner;

public class Blackjackold {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static double balance = 100;
    static int playerHandTotal;
    static int dealerHandTotal;
    static double betPlaced;
    static int totalCardValue;
    static String playerChoice;
    static String winCheck;
    static boolean playerBust;
    static boolean dealerBust;

    public static void main(String[] args){

        // BLACKJACK

        System.out.println("Welcome to Blackjack!");
        do{
            //Resets values each round
            dealerBust = false;
            playerBust = false;
            winCheck = "";

            // Start of game
            System.out.printf("You have $%.2f remaining.\n", balance);
            do{
                System.out.print("Place a bet (min $10): ");
                betPlaced = scanner.nextDouble();
                if(betPlaced < 10){
                    System.out.println("Your bet did not meet the minimum bet.");
                }
            }while(betPlaced < 10);
            scanner.nextLine();
            balance = balance - betPlaced;
            winCheck = round();
            if(winCheck.contains("BJ")){
                if(winCheck.contains("draw")){
                    System.out.println("Unlucky! Both parties drew a blackjack, your bet has been returned to you.");
                    balance = balance + betPlaced;
                } else if(winCheck.contains("player")){
                    betPlaced = betPlaced + (betPlaced * 1.5);
                    System.out.printf("Super Lucky!! You had a blackjack. You have won $%.2f!\n", betPlaced);
                    balance = balance + betPlaced;
                } else {
                    System.out.println("Super unlucky! Dealer has a blackjack. You have lost your bet.");
                }
            } else {
                if(winCheck.equals("draw")){
                    System.out.println("Unlucky! It was a draw. Your bet has been returned to you.");
                    balance = balance + betPlaced;
                } else if(winCheck.equals("player")){
                    betPlaced = betPlaced + betPlaced;
                    System.out.printf("Winner! You have beaten the dealer! You have won $%.2f.\n", betPlaced);
                    balance = balance + betPlaced;
                } else {
                    System.out.println("Unlucky! The dealer has beaten you. You have lost your bet.");
                }
            }

        }while(balance >= 10);
        System.out.println("You have run out of money! Quitting game...");

        scanner.close();

    }

    static String round(){
        playerHandTotal = createHand();
        dealerHandTotal = createHand();
        do{
            // Checks for blackjack
            if(playerHandTotal == 21 && dealerHandTotal == 21){
                winCheck = "drawBJ";
            }
            else if(dealerHandTotal == 21){
                winCheck = "dealerBJ";
            }
            else if(playerHandTotal == 21){
                winCheck = "playerBJ";
            }

            // Standard round being played
            else{
                do{
                    // Player makes choice to hit or stand
                    System.out.println("Your hand point total is: " + playerHandTotal);
                    do{
                        System.out.print("Enter what action you would like to take (hit or stand): ");
                        playerChoice = scanner.nextLine().toLowerCase();
                        if(!playerChoice.equals("hit") && !playerChoice.equals("stand")){
                            System.out.println("Invalid input. Please enter 'hit' or 'stand'.");
                        }
                    }while(!playerChoice.equals("hit") && !playerChoice.equals("stand"));

                    // Processes the choice
                    if(playerChoice.equals("hit")){
                        System.out.println("You chose to 'hit'! Drawing a new card...");
                        playerHandTotal = playerHandTotal + random.nextInt(1, 11);
                        if(playerHandTotal > 21){
                            playerBust = true;
                            break;
                        }
                    } else {
                        System.out.println("You chose to stand with a total of " + playerHandTotal);
                    }

                }while(!playerChoice.equals("stand") && !playerBust);

                // Performs dealer card draws until total > 17 and checks for bust
                if(!playerBust) {
                    do {
                        dealerHandTotal = dealerHandTotal + random.nextInt(1, 11);
                        System.out.println("Dealer is drawing another card..");
                        if (dealerHandTotal > 21) {
                            dealerBust = true;
                        }
                    } while (dealerHandTotal < 17);
                }

                // Does final calculation and reports a win
                if(playerBust){
                    winCheck = "dealer";
                    System.out.println("You have busted!");
                } else if(dealerBust){
                    winCheck = "player";
                    System.out.println("The dealer busted!");
                } else {
                    if(playerHandTotal > dealerHandTotal){
                        winCheck = "player";
                    }
                    else if(playerHandTotal == dealerHandTotal){
                        winCheck = "draw";
                    }
                    else {
                        winCheck = "dealer";
                    }
                }

            } // Plays the round until bust

        }while(winCheck.isEmpty());

        // Outputs both parties' card totals.
        System.out.println("The round is over!");
        System.out.println("Your card total: " + playerHandTotal);
        System.out.println("Dealer card total: " + dealerHandTotal);

        //returns the winning party.
        return winCheck;
    }

    static int createHand(){
        int cardValue1 = random.nextInt(1, 11);
        int cardValue2 = random.nextInt(1, 11);
        if((cardValue1 == 1 && cardValue1 == cardValue2) || (cardValue1 == 11 && cardValue1 == cardValue2)){
            totalCardValue = 2;
            // CHECKS FOR DOUBLE ACES
        }
        else if((cardValue1 == 1 && cardValue2 == 10) || (cardValue1 == 11 && cardValue2 == 10) || (cardValue1 == 10 && cardValue2 == 1) || (cardValue1 == 10 && cardValue2 == 11)){
            totalCardValue = 21;
            // CHECKS FOR BLACKJACK TOTAL VALUE
        }
        else{
            totalCardValue = cardValue1 + cardValue2;
            // ELSE RETURNS CARD VALUE
        }
        return totalCardValue;
    }

}