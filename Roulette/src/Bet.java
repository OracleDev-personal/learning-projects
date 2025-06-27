import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Bet {

    String type;
    String value;
    int amount;

    Set<String> halves = new LinkedHashSet<>(List.of(
            "red",
            "black",
            "odds",
            "evens",
            "1-18",
            "19-36"));
    Set<String> dozens = new LinkedHashSet<>(List.of(
            "1-12",
            "13-24",
            "25-36",
            "column 1",
            "column 2",
            "column 3"
    ));

    private final Scanner scanner;

    public Bet(Scanner scanner) { this.scanner = scanner; }

    public void getBetAmount(){
        do {
            System.out.print("Enter the bet amount: ");
            this.amount = scanner.nextInt();
            scanner.nextLine();
            if(amount <= 0) {
                System.out.println("Bet must be greater than 0!\n");
            }
        } while(amount <= 0);
    }

    public void getBetType(){
        do {
            System.out.print("Enter the type of bet (halves, singles, dozens): ");
            this.type = scanner.nextLine();
            if(!type.equals("halves") && !type.equals("singles") && !type.equals("dozens")) {
                System.out.printf("Invalid input!\n");
            }
        } while (!type.equals("halves") && !type.equals("singles") && !type.equals("dozens"));
    }

    public void getBetValue(String type){
        boolean validBet = false;

        do {
            System.out.println("Choose a type of bet.");
            System.out.println("\nChoices: ");
            switch (type) {
                case "halves" -> {
                    for(String entry : halves){
                        System.out.println(entry);
                    }
                }
                case "singles" -> System.out.println("Numbers 1-36 (select one)");
                case "dozens" -> {
                    for(String entry : dozens){
                        System.out.println(entry);
                    }
                }
            }
            System.out.print("\nEnter your choice: ");
            this.value = scanner.nextLine();
            validBet = checkValidBet(type, value);
            if(!validBet) {
                System.out.println("Invalid bet!\n");
            }
        } while (!validBet);
    }

    boolean checkValidBet(String type, String value){
        switch (type) {
            case "halves" -> {
                if(halves.contains(value)){
                    return true;
                    }
            }
            case "singles" -> {
                int v = Integer.parseInt(value);
                if(v >= 1 && v <= 36){
                    return true;
                }

            }
            case "dozens" -> {
                if(dozens.contains(value)){
                    return true;
                }
            }
        }

        return false;
    }

    void provideTypes() {
        System.out.println("""
                
                ============ Bet Types ==========
                * Halves (1:1)
                  * Red or Black
                  * Odds or Even
                  * 1-18 or 19-36
                * Single number (35:1)
                * Dozens (2:1)
                  * 1st 12 (1-12)
                  * 2nd 12 (13-24)
                  * 3rd 12 (25-36)
                  * Column 1 (1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34)
                  * Column 2 (2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35)
                  * Column 3 (3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36)
                
                """);
    }

    public String getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

}