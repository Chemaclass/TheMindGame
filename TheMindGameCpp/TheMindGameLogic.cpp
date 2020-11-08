
#include <map>
#include <utility>
#include <vector>
#include <list>
#include <iostream>
#include "TheMindGameLogic.h"
#include "Player.h"

TheMindGameLogic::TheMindGameLogic(int numPlayers, int numLevelsToWin) {

    this->numPlayers = numPlayers;
    this->numLevelsToWin = numLevelsToWin;
    this->failedGames = 0;
    this->currentLevel = 1;
}

TheMindGameLogicResult TheMindGameLogic::play() {
    std::map<int, std::vector<Card>> successfulPiles;

    while (currentLevel <= numLevelsToWin) {
        std::vector<Card> pileOfCards;
        int desiredPileOfCardsNumber = numPlayers * currentLevel;
        std::vector<Player> players = dealCardsToEachPlayer();

        do {
            Card currentCard = popRandomlyOnePlayerCard(players);

            if (!isValidCardInPile(currentCard, pileOfCards)) {
                failedGames++;
                currentLevel = 1;
                successfulPiles.clear();
                displayDebugInfo(currentCard, pileOfCards);
                break;
            }

            pileOfCards.push_back(currentCard);
        } while (areStillCardsToPlay(players));

        // Don't level-up if the pile miss cards
        if (pileOfCards.size() < desiredPileOfCardsNumber) {
            continue;
        }

        std::pair<int, std::vector<Card>> levelWithPile(currentLevel, pileOfCards);
        successfulPiles.insert(levelWithPile);
        currentLevel++;
    }

    return TheMindGameLogicResult(failedGames, successfulPiles);
}

std::vector<Player> TheMindGameLogic::dealCardsToEachPlayer() {
    std::vector<Player> players;

    for (int i = 0; i < numPlayers; i++) {
        Player player = Player::create(currentLevel);
        players.push_back(player);
    }

    return players;
}

bool TheMindGameLogic::areStillCardsToPlay(std::vector<Player> players) {

    std::vector<Player> playersWithCards = getPlayersWithCards(std::move(players));

    return !playersWithCards.empty();
}

Card TheMindGameLogic::popRandomlyOnePlayerCard(std::vector<Player> players) {
    std::vector<Player> playersWithCards = getPlayersWithCards(std::move(players));
    int randomPos = rand() % playersWithCards.size() + 0;

    return playersWithCards[randomPos].popMinCard();
}

std::vector<Player> TheMindGameLogic::getPlayersWithCards(std::vector<Player> players) {
    std::vector<Player> playersWithCards;

    std::copy_if(
            players.begin(),
            players.end(),
            std::back_inserter(playersWithCards),
            [](const Player &player) {
                return player.hasCards();
            }
    );

    return playersWithCards;
}

bool TheMindGameLogic::isValidCardInPile(Card currentCard, const std::vector<Card> &pileOfCards) {
    if (pileOfCards.empty()) {
        return true;
    }

    return currentCard.getNumber() >= getLastPileCard(pileOfCards).getNumber();
}

Card TheMindGameLogic::getLastPileCard(std::vector<Card> pileOfCards) {
    return *pileOfCards.erase(pileOfCards.begin());
}

void TheMindGameLogic::displayDebugInfo(Card currentCard, std::vector<Card> pileOfCards) const {
    std::string debugMessage = reinterpret_cast<const char *>(printf(
            "Failed game Nr %d. Trying again... brrr (current:%d, last:%d)\n",
            failedGames,
            currentCard.getNumber(),
            getLastPileCard(std::move(pileOfCards)).getNumber()
    ));

    std::cout << debugMessage << std::endl;
}
