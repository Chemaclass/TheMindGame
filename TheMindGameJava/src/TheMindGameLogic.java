import ReadModel.Card;
import ReadModel.Player;

import java.util.*;
import java.util.stream.Collectors;

public class TheMindGameLogic {
    public static final int FAILED_GAMES_DEBUG = 2_500_000;

    private static int failedGames = 0;
    private static boolean keepRunningThread = true;

    private final int numPlayers;
    private final int numLevelsToWin;

    private int currentLevel = 1;

    public TheMindGameLogic(int numPlayers, int numLevelsToWin) {
        this.numPlayers = numPlayers;
        this.numLevelsToWin = numLevelsToWin;
    }

    public static void stopRunningThreads() {
        keepRunningThread = false;
    }

    public TheMindGameLogicResult play() {
        Map<Integer, List<Card>> successfulPiles = new HashMap<>();

        while (stillLookingForSolution()) {
            List<Card> pileOfCards = new ArrayList<>();
            int desiredPileOfCardsNumber = numPlayers * currentLevel;
            List<Player> players = dealCardsToEachPlayer(numPlayers, currentLevel);

            if (isDebugEnabled()) {
                System.err.println(players);
            }

            do {
                Card currentCard = popRandomlyOnePlayerCard(players);

                if (!isValidCardInPile(currentCard, pileOfCards)) {
                    incrementFailedGames();
                    currentLevel = 1;
                    successfulPiles = new HashMap<>();
                    if (isDebugEnabled()) {
                        System.out.println("Failed game number " + failedGames + ". Trying again... brrr");
                    }
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

        if (successfulPiles.size() != numLevelsToWin) {
            return TheMindGameLogicResult.createEmpty();
        }

        return new TheMindGameLogicResult(failedGames, successfulPiles);
    }

    private synchronized void incrementFailedGames() {
        failedGames++;
    }

    private boolean stillLookingForSolution() {
        return currentLevel <= numLevelsToWin
                && keepRunningThread;
    }

    private List<Player> dealCardsToEachPlayer(int numPlayers, int currentLevel) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            players.add(Player.create(currentLevel, players));
        }

        return players;
    }

    private boolean isDebugEnabled() {
        return failedGames % FAILED_GAMES_DEBUG == 0;
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
