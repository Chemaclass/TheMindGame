import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PlayerCards {

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 150;

    private List<Card> cards;

    public static PlayerCards create(int currentLevel, List<PlayerCards> existingPlayerCards) {
        List<Card> cards = new ArrayList<>();
        List<Integer> allNumbers = new ArrayList<>();

        for (PlayerCards existingPlayerCard : existingPlayerCards) {
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

        return new PlayerCards(cards);
    }

    private PlayerCards(List<Card> cards) {
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

    public List<Card> cards() {
        return this.cards;
    }

    public boolean hasCards() {
        return totalCards() > 0;
    }
}
