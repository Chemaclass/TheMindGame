<?php

declare(strict_types=1);

namespace App;

/**
 * @psalm-immutable
 */
final class TheMindGame
{
    public function play(int $numPlayers, int $numLevelsToWin): TheMindGameResult
    {
        $failedGames = 0;
        $pileOfCards = [];
        $result = [];
        $currentLevel = 2;

        while ($currentLevel <= $numLevelsToWin) {
            $desiredPileOfCardsNumber = $numPlayers *$currentLevel;
            $players = $this->dealCardsToEachPlayer($numPlayers, $currentLevel);

            do {
                $currentCard = $this->popRandomlyOnePlayerCard($players);

                if (!$this->isValidCardInPile($currentCard, ...$pileOfCards)) {
                    $failedGames++;
                    $currentLevel = 1;
                    $result = [];
                    break;
                }

                $pileOfCards[] = $currentCard;
            } while ($this->areStillCardsToPlay(...$players));

            if ($desiredPileOfCardsNumber === count($pileOfCards)) {
                $result[$currentLevel] = $pileOfCards;
                $pileOfCards = [];
                $currentLevel++;
            }
        }

        return TheMindGameResult::create($failedGames, $result);
    }

    private function dealCardsToEachPlayer(int $numPlayers, int $currentLevel): array
    {
        $players = [];

        for ($i = 0; $i < $numPlayers; $i++) {
            $players[] = PlayerCards::create($currentLevel, ...$players);
        }

        return $players;
    }

    private function isValidCardInPile(Card $currentCard, Card ...$pileOfCards): bool
    {
        $lastCard = end($pileOfCards);
        if (!$lastCard) {
            return true;
        }

        return $currentCard->number() >= $lastCard->number();
    }

    private function popRandomlyOnePlayerCard(array $players): Card
    {
        /** @var PlayerCards[] $playersWithCards */
        $playersWithCards = array_values(array_filter(
            $players,
            static fn(PlayerCards $playerCards) => $playerCards->totalCards() > 0
        ));

        $posRandom = random_int(0, count($playersWithCards) - 1);
        $randomPlayer = $playersWithCards[$posRandom];

        return $randomPlayer->popMinCard();
    }

    private function areStillCardsToPlay(PlayerCards ...$players): bool
    {
        $playersWithCards = array_filter(
            $players,
            static fn(PlayerCards $playerCards) => $playerCards->totalCards() > 0
        );

        return count($playersWithCards) > 0;
    }
}
