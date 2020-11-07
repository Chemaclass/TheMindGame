import java.util.*;

public class Player {
    private static final int MAX_VALUE = 100;

    private final LinkedList<Card> cards;

    /**
     * A player consist of a list of (already-sorted) cards
     */
    public static Player create(int currentLevel, List<Player> existingPlayerCards) {
        Card[] cards = new Card[currentLevel];
        List<Integer> allNumbers = new ArrayList<>();

        for (Player existingPlayerCard : existingPlayerCards) {
            for (Card card : existingPlayerCard.cards) {
                allNumbers.add(card.number());
            }
        }

        for (int i = 0; i < currentLevel; i++) {
            int cardNumber;
            do {
                cardNumber = new Random().nextInt(MAX_VALUE);
            } while (allNumbers.contains(cardNumber));

            allNumbers.add(cardNumber);
            cards[i] = new Card(cardNumber);
        }
        Arrays.sort(cards);

        return new Player(new LinkedList<>(Arrays.asList(cards)));
    }

    private Player(LinkedList<Card> cards) {
        this.cards = cards;
    }

    public Card popMinCard() {
        return this.cards.pop();
//        Card minCard = this.cards
//                .stream()
//                .min(Comparator.comparing(Card::number))
//                .get();
//
//        this.cards.remove(minCard);
//
//        return minCard;
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
