import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Player {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 150;

    private final List<Card> cards;

    public static Player create(int currentLevel, List<Player> existingPlayerCards) {
        List<Card> cards = new ArrayList<>();
        List<Integer> allNumbers = new ArrayList<>();

        for (Player existingPlayerCard : existingPlayerCards) {
            for (Card card : existingPlayerCard.cards) {
                allNumbers.add(card.number());
            }
        }

        for (int i = 0; i < currentLevel; i++) {
            int cardNumber;
            do {
                cardNumber = new Random().nextInt(MAX_VALUE) + 1;
            } while (allNumbers.contains(cardNumber));

            allNumbers.add(cardNumber);
            cards.add(new Card(cardNumber));
        }

        return new Player(cards);
    }

    private Player(List<Card> cards) {
        this.cards = cards;
    }

    public Card popMinCard() {
        Card minCard = this.cards
                .stream()
                .min(Comparator.comparing(Card::number))
                .get();

        this.cards.remove(minCard);

        return minCard;
    }

    public int totalCards() {
        return this.cards.size();
    }

    public boolean hasCards() {
        return totalCards() > 0;
    }

    @Override
    public String toString() {
        return "- " + cards;
    }
}
