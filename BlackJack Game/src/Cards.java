import java.util.ArrayList;
import java.util.Random;

public class Cards {

    String cardFace;
    int cardValue;

    public Cards(String cardFace, int cardValue) {
        this.cardFace = cardFace;
        switch (cardFace) {
            case "A" -> this.cardValue = 11;
            case "J", "Q", "K" -> this.cardValue = 10;
            default -> this.cardValue = cardValue;
        }
    }

    public String toString() {
        return switch (cardFace) {
           case "J", "Q", "K" -> cardFace + " (10)";
           case "A" -> cardFace + " (1 or 11)";
           default -> cardFace + " (" + cardValue + ")";
        };
    }

}
