import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    String name;
    int bal = 100;
    List<Bet> bets = new ArrayList<>();


    private final Scanner scanner;

    public Player(String name, Scanner scanner) {
        this.scanner = scanner;
        this.name = name;
    }

    public int placeBets() {
        String again;
        int betsPlaced = 0;
        do {;
            Bet bet = placeBet();
            bets.add(bet);

            betsPlaced++;
            System.out.print("Place another bet? (Y/N): ");
            again = scanner.nextLine().toUpperCase();
        } while (again.equals("Y"));
        System.out.println();
        return betsPlaced;
    }

    public Bet placeBet() {
        Bet bet = new Bet(scanner);
        boolean validBet = false;
        do {
            bet.getBetAmount();
            bet.provideTypes();
            bet.getBetType();
            bet.getBetValue(bet.type);
            validBet = bet.checkValidBet(bet.type, bet.value);
            if (validBet) {
                bal = bal - bet.amount;
                System.out.println("Your new balance is " + bal);
            }
            else {
                System.out.println("Invalid entry, please provide a valid bet.");
            }
        } while (!validBet);
        return bet;
    }



}
