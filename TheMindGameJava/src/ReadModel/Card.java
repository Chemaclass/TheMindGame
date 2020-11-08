package ReadModel;

public class Card implements Comparable<Card> {
    private final int number;

    public Card(int number) {
        this.number = number;
    }

    public int number() {
        return this.number;
    }

    @Override
    public String toString() {
        return "Card(" + number + ')';
    }

    @Override
    public int compareTo(Card o) {
        return this.number >= o.number ? 1 : 0;
    }
}
