import java.util.*;

public class Wheel {

    static Random random = new Random();

    HashMap<Integer, Character> wheel = new HashMap<>();
    int[] red = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    int[] black = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
    int[] lastWins = new int[5];

    public void createWheel() {
        for(int i = 0; i < red.length; i++) {
            this.wheel.put(red[i], 'r');
            this.wheel.put(black[i], 'b');
        }
        this.wheel.put(0, 'g');
    }

    public Map<Integer, Character> spinWheel(){
        Map<Integer, Character> winningNum = new HashMap<>();
        int num = random.nextInt(37);
        winningNum.put(num, wheel.get(num));
        addLastWin(num);
         System.out.print("The ball landed on: " + num + ", ");
         switch (wheel.get(num)) {
             case 'r' -> System.out.print("Red");
             case 'b' -> System.out.print("Black");
             case 'g' -> System.out.print("Green");
         }
         System.out.println();
        return winningNum;
    }

    void addLastWin(int num) {
        for(int i = lastWins.length - 1; i > 0; i--) {
                lastWins[i] = lastWins[i - 1];
            }
        lastWins[0] = num;
    }

    public void getLastWins() {
        System.out.println("\nThe last 5 wins were:");
        for(int win : lastWins) {
            System.out.println(win + ", " + wheel.get(win) + "\n");
        }
    }
}
