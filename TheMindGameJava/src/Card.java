public class Card {
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
}
