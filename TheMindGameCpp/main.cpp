#include <iostream>
#include "TheMindGameLogic.h"
#include "TheMindGameLogicResult.h"

int main() {
    std::cout << "Hello, World!" << std::endl;

    int numPlayers = 2, numLevelsToWin = 2;

    TheMindGameLogic logic(numPlayers, numLevelsToWin);
    TheMindGameLogicResult result = logic.play();

    std::cout << result.toString() << std::endl;
    return 0;
}
