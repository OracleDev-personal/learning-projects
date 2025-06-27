import java.util.*;

public class RouletteGame {

    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        RouletteGame game = new RouletteGame();
        Wheel gameWheel = new Wheel();
        gameWheel.createWheel();
        String playerResponse;
        int betsPlaced = 0;
        int betsWon = 0;
        int totalWinnings = 0;
        int roundNum = 1;

        System.out.println("********************");
        System.out.println("Welcome to Roulette!");
        System.out.println("********************");

        System.out.print("Enter a name to refer by: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName, scanner);
        Thread.sleep(750);

        System.out.println("\nNice to meet you " + playerName);

        do {
            System.out.print("Do you know how to play roulette? (Y/N) ");
            playerResponse = scanner.nextLine().toUpperCase();
            if(playerResponse.equals("N")) {
                System.out.println("""
                        In roulette, there is a wheel with several numbers ranging from 0-36 present. At the beginning of each round,
                        the player will place a bet based on a variety of options for those numbers. After bets are placed, a ball
                        will be sent around and will stop at a given number. If the ball lands on the number the player placed a bet
                        on (or relates to their bet), the player will receive a payout. Else, the player will lose their bet.
                        \n""");
                break;
            }
            else if (!playerResponse.equals("Y")) {
                System.out.println("Invalid response!");
            }
        } while(!playerResponse.equals("Y"));
        playerResponse = "";
        System.out.println("Let's play!");
        do {
            System.out.println("Your current balance is: " + player.bal);

            player.bets.clear();
            if(roundNum != 1) {
                gameWheel.getLastWins();
            }
            betsPlaced += player.placeBets(); // take bets - multiple allowed

            System.out.println("Spinning the wheel...");
            Thread.sleep(3000);
            Set<String> winCriteria = game.getWinCriteria(gameWheel);
            totalWinnings += game.payoutWin(winCriteria, player.bets, player);

            do {
                System.out.print("Do you want to play another round? (Y/N): ");
                playerResponse = scanner.nextLine().toUpperCase();
                if(!playerResponse.equals("Y") && !playerResponse.equals("N")) {
                    System.out.println("Invalid response!");
                }
            } while(!playerResponse.equals("Y") && !playerResponse.equals("N"));
            roundNum++;
        } while (playerResponse.equals("Y") && player.bal > 0);

        System.out.println("Nice work! You played for a total of " + roundNum + " rounds. With a remaining balance of " + player.bal + ", you won a total of $" + totalWinnings + ".");
        System.out.println("See you next time.");

        scanner.close();
    }

    Set<String> getWinCriteria(Wheel wheel) {
        Set<Integer> column1 = Set.of(1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34);
        Set<Integer> column2 = Set.of(2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35);
        Set<Integer> column3 = Set.of(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36);
        Map<Integer, Character> wheelSpin = wheel.spinWheel();
        Set<String> winCriteria = new HashSet<>();
        int num = wheelSpin.keySet().iterator().next();
        char color = wheelSpin.get(num);
        // color categories
        switch (color) {
            case 'r' -> winCriteria.add("red");
            case 'b' -> winCriteria.add("black");
        }
        // even/odd/0
        if(num == 0) {
            winCriteria.add("0");
        } else if(num % 2 == 0) {
            winCriteria.add("evens");
        } else if (num % 2 == 1){
            winCriteria.add("odds");
        }
        // columns
        if(column1.contains(num)) {
            winCriteria.add("column 1");
        } else if (column2.contains(num)) {
            winCriteria.add("column 2");
        } else if (column3.contains(num)) {
            winCriteria.add("column 3");
        }
        // dozens
        if(num >= 1 && num <= 12) {
            winCriteria.add("1-12");
        } else if (num >= 13 && num <= 24) {
            winCriteria.add("13-24");
        } else if (num >= 25 && num <= 36) {
            winCriteria.add("25-36");
        }
        // halves
        if(num >= 1 && num <= 18) {
            winCriteria.add("1-18");
        } else if(num >= 19 && num <= 36) {
            winCriteria.add("19-36");
        }
        if(num != 0) {
            String numToString = String.valueOf(num);
            winCriteria.add(numToString);
        }
        return winCriteria;
    }

    int payoutWin (Set<String> winCriteria, List<Bet> bets, Player player) {
        int totalWinnings = 0;
        for(Bet bet : bets) {
            String type = bet.type; // type = halves, dozens, singles
            String value = bet.value; // value = which one
            boolean criteriaMet = winCriteria.contains(value);
            int amount = bet.amount;
            if(type.equals("singles") && criteriaMet) {
                totalWinnings = amount + (amount * 35);
                player.bal += amount + (amount * 35);
                System.out.printf("Congrats %s! You won $%d!\n", player.name, totalWinnings);
            }
            else if(type.equals("halves") && criteriaMet) {
                totalWinnings = (amount * 2);
                player.bal += (amount * 2);
                System.out.printf("Congrats %s! You won $%d!\n", player.name, totalWinnings);
            }
            else if(type.equals("dozens") && criteriaMet) {
                totalWinnings = amount + (amount * 2);
                player.bal += amount + (amount * 2);
                System.out.printf("Congrats %s! You won $%d!\n", player.name, totalWinnings);
            }
        }
        if(totalWinnings == 0) {
            System.out.println("Sorry! You did not win anything! Keep playing!\n");
        }
        return totalWinnings;
    }
}

