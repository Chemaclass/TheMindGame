
#ifndef UNTITLED_PLAYER_H
#define UNTITLED_PLAYER_H


#include <array>
#include "Card.h"

class Player {
public:
    static Player create(int currentLevel);

    explicit Player(std::vector<Card> & cards);

    [[nodiscard]] bool hasCards() const;

    Card popMinCard();

private:
    std::vector<Card> *cards;
};

#endif //UNTITLED_PLAYER_H
