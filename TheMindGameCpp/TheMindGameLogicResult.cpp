
#include "TheMindGameLogicResult.h"

#include <utility>
#include <string>

TheMindGameLogicResult::TheMindGameLogicResult(int failedGames, std::map<int, std::vector<Card>> result) {
    this->failedGames = failedGames;
    this->result = std::move(result);
}

std::string TheMindGameLogicResult::toString() const {
    std::string resultAsString = "TODO: // a loop...";

    return "failedGames=" + std::to_string(failedGames) + ", " + resultAsString;
}

