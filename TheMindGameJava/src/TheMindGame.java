import java.util.*;
import java.util.stream.Collectors;

public class TheMindGame {

    public static void main(String[] args) {
        int numPlayers = 2, numLevelsToWin = 2;

        TheMindGame theMindGame = new TheMindGame();
        TheMindGameResult result = theMindGame.play(numPlayers, numLevelsToWin);

        System.out.println(result);
    }

    public TheMindGameResult play(int numPlayers, int numLevelsToWin) {
        int failedGames = 0, currentLevel = 1;
        List<Card> pileOfCards = new ArrayList<>();
        Map<Integer, List<Card>> result = new HashMap<>();

        while (currentLevel <= numLevelsToWin) {
            int desiredPileOfCardsNumber = numPlayers * currentLevel;
            List<PlayerCards> players = this.dealCardsToEachPlayer(numPlayers, currentLevel);

            do {
                Card currentCard = this.popRandomlyOnePlayerCard(players);

                if (!this.isValidCardInPile(currentCard, pileOfCards)) {
                    failedGames++;
                    currentLevel = 1;
                    result = new HashMap<>();
                    System.out.println("Failed game number " + failedGames + ". Trying again... brrr");
                    break;
                }

                pileOfCards.add(currentCard);
            } while (this.areStillCardsToPlay(players));

            if (desiredPileOfCardsNumber == pileOfCards.size()) {
                result.put(currentLevel, pileOfCards);
                pileOfCards = new ArrayList<>();
                currentLevel++;
            }
        }

        return new TheMindGameResult(failedGames, result);
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
                .anyMatch(c -> c.totalCards() > 0);
    }
}
