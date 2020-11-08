
#include <vector>
#include "Player.h"
#include "Card.h"

Player Player::create(const int currentLevel) {
    std::vector<Card> cards[currentLevel];
    Player player(*cards);

    return player;
}

Player::Player(std::vector<Card> &cards) {
    this->cards = &cards;
}

bool Player::hasCards() const{
    return !this->cards->empty();
}

Card Player::popMinCard() {
    return *this->cards->erase(this->cards->begin());
}
