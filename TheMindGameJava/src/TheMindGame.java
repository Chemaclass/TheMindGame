import java.util.*;
import java.util.stream.Collectors;

public class TheMindGame {
    private final int numPlayers;
    private final int numLevelsToWin;

    private int failedGames = 0;
    private int currentLevel = 1;

    public static void main(String[] args) {
        int numPlayers = 3, numLevelsToWin = 3;
        TheMindGameResult result = new TheMindGame(numPlayers, numLevelsToWin).play();
        System.out.println(result);
    }

    public TheMindGame(int numPlayers, int numLevelsToWin) {
        this.numPlayers = numPlayers;
        this.numLevelsToWin = numLevelsToWin;
    }

    public TheMindGameResult play() {
        Map<Integer, List<Card>> successfulPiles = new HashMap<>();

        while (currentLevel <= numLevelsToWin) {
            List<Card> pileOfCards = new ArrayList<>();
            int desiredPileOfCardsNumber = numPlayers * currentLevel;
            List<Player> players = dealCardsToEachPlayer(numPlayers, currentLevel);
            System.err.println(players);

            do {
                Card currentCard = popRandomlyOnePlayerCard(players);

                if (!isValidCardInPile(currentCard, pileOfCards)) {
                    failedGames++;
                    currentLevel = 1;
                    successfulPiles = new HashMap<>();
                    System.out.println("Failed game number " + failedGames + ". Trying again... brrr");
                    break;
                }

                pileOfCards.add(currentCard);
            } while (areStillCardsToPlay(players));

            // Don't level-up if the pile miss cards
            if (pileOfCards.size() < desiredPileOfCardsNumber) {
                continue;
            }

            successfulPiles.put(currentLevel, pileOfCards);
            currentLevel++;
        }

        return new TheMindGameResult(failedGames, successfulPiles);
    }

    private List<Player> dealCardsToEachPlayer(int numPlayers, int currentLevel) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            players.add(Player.create(currentLevel, players));
        }

        return players;
    }

    private boolean isValidCardInPile(Card currentCard, List<Card> pileOfCards) {
        if (pileOfCards.isEmpty()) {
            return true;
        }

        Card lastCard = pileOfCards.get(pileOfCards.size() - 1);

        return currentCard.number() >= lastCard.number();
    }

    private Card popRandomlyOnePlayerCard(List<Player> players) {
        List<Player> playersWithCards = players
                .stream()
                .filter(Player::hasCards)
                .collect(Collectors.toList());

        int randomPos = new Random().nextInt(playersWithCards.size());
        Player randomPlayer = playersWithCards.get(randomPos);

        return randomPlayer.popMinCard();
    }

    private boolean areStillCardsToPlay(List<Player> players) {
        return players
                .stream()
                .anyMatch(Player::hasCards);
    }
}
