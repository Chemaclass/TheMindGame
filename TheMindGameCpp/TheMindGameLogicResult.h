
#ifndef UNTITLED_THEMINDGAMELOGICRESULT_H
#define UNTITLED_THEMINDGAMELOGICRESULT_H


#include <map>
#include "Card.h"

class TheMindGameLogicResult {

public:
    TheMindGameLogicResult(int failedGames, std::map<int, std::vector<Card>> result);
    [[nodiscard]] std::string toString() const;
private:
    int failedGames;
    std::map<int, std::vector<Card>> result;
};


#endif //UNTITLED_THEMINDGAMELOGICRESULT_H
