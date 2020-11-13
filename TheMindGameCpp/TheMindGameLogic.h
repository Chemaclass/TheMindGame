
#ifndef UNTITLED_THEMINDGAMELOGIC_H
#define UNTITLED_THEMINDGAMELOGIC_H


#include "TheMindGameLogicResult.h"
#include "Player.h"

class TheMindGameLogic {
private:
    int failedGames;
    int numPlayers;
    int numLevelsToWin;
    int currentLevel;
public:
    TheMindGameLogic(int numPlayers, int numLevelsToWin);

    TheMindGameLogicResult play();

    std::vector<Player> dealCardsToEachPlayer();

    static bool areStillCardsToPlay(std::vector<Player> pl);

    static Card popRandomlyOnePlayerCard(std::vector<Player> players);

    static std::vector<Player> getPlayersWithCards(std::vector<Player> players);

    static bool isValidCardInPile(Card card, const std::vector<Card>& vector);

    static Card getLastPileCard(std::vector<Card> pileOfCards);

    void displayDebugInfo(Card card, std::vector<Card> vector) const;
};

#endif //UNTITLED_THEMINDGAMELOGIC_H
