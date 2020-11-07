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
        List<Card> pileOfCards = new ArrayList<>();
        Map<Integer, List<Card>> successfulPiles = new HashMap<>();

        while (currentLevel <= numLevelsToWin) {
            int desiredPileOfCardsNumber = numPlayers * currentLevel;
            List<PlayerCards> players = dealCardsToEachPlayer(numPlayers, currentLevel);

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

            if (pileOfCards.size() >= desiredPileOfCardsNumber) {
                successfulPiles.put(currentLevel, pileOfCards);
                pileOfCards = new ArrayList<>();
                currentLevel++;
            }
        }

        return new TheMindGameResult(failedGames, successfulPiles);
    }

    private List<PlayerCards> dealCardsToEachPlayer(int numPlayers, int currentLevel) {
        List<PlayerCards> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            players.add(PlayerCards.create(currentLevel, players));
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

    private Card popRandomlyOnePlayerCard(List<PlayerCards> players) {
        List<PlayerCards> playersWithCards = players
                .stream()
                .filter(PlayerCards::hasCards)
                .collect(Collectors.toList());

        int randomPos = new Random().nextInt(playersWithCards.size());
        PlayerCards randomPlayer = playersWithCards.get(randomPos);

        return randomPlayer.popMinCard();
    }

    private boolean areStillCardsToPlay(List<PlayerCards> players) {
        return players
                .stream()
                .anyMatch(PlayerCards::hasCards);
    }
}
