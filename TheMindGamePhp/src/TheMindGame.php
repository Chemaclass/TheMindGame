<?php

declare(strict_types=1);

namespace App;

/**
 * @psalm-immutable
 */
final class TheMindGame
{
    private const FAILED_GAMES_DEBUG = 500_000;
    private static int $failedGames = 0;

    private int $currentLevel = 1;

    public function play(int $numPlayers, int $numLevelsToWin): TheMindGameResult
    {
        $successfulPiles = [];

        while ($this->currentLevel <= $numLevelsToWin) {
            $pileOfCards = [];
            $desiredPileOfCardsNumber = $numPlayers * $this->currentLevel;
            $players = $this->dealCardsToEachPlayer($numPlayers);

            do {
                $currentCard = $this->popRandomlyOnePlayerCard($players);

                if (!$this->isValidCardInPile($currentCard, $pileOfCards)) {
                    static::$failedGames++;
                    $this->currentLevel = 1;
                    $successfulPiles = [];
                    $this->displayDebugInfo($currentCard, $pileOfCards);
                    break;
                }

                $pileOfCards[] = $currentCard;
            } while ($this->areStillCardsToPlay(...$players));

            // Don't level-up if the pile miss cards
            if (count($pileOfCards) < $desiredPileOfCardsNumber) {
                continue;
            }

            $successfulPiles[$this->currentLevel] = $pileOfCards;
            $this->currentLevel++;
        }

        if (count($successfulPiles) !== $numLevelsToWin) {
            return TheMindGameResult::createEmpty();
        }

        return TheMindGameResult::create(static::$failedGames, $successfulPiles);
    }

    /**
     * @return Player[]
     */
    private function dealCardsToEachPlayer(int $numPlayers): array
    {
        $players = [];

        for ($i = 0; $i < $numPlayers; $i++) {
            $players[] = Player::create($this->currentLevel, ...$players);
        }

        return $players;
    }

    /**
     * @param Card[] $pileOfCards
     */
    private function isValidCardInPile(Card $currentCard, array $pileOfCards): bool
    {
        $lastCard = $this->lastPileCard($pileOfCards);
        if (!$lastCard) {
            return true;
        }

        return $currentCard->number() >= $lastCard->number();
    }

    /**
     * @param Card[] $pileOfCards
     */
    private function lastPileCard(array $pileOfCards): ?Card
    {
        $lastCard = end($pileOfCards);

        return (!$lastCard) ? null : $lastCard;
    }

    private function popRandomlyOnePlayerCard(array $players): Card
    {
        /** @var Player[] $playersWithCards */
        $playersWithCards = array_values(array_filter(
            $players,
            static fn(Player $playerCards) => $playerCards->totalCards() > 0
        ));

        $posRandom = random_int(0, count($playersWithCards) - 1);
        $randomPlayer = $playersWithCards[$posRandom];

        return $randomPlayer->popMinCard();
    }

    private function areStillCardsToPlay(Player ...$players): bool
    {
        $playersWithCards = array_filter(
            $players,
            static fn(Player $playerCards) => $playerCards->totalCards() > 0
        );

        return count($playersWithCards) > 0;
    }

    /**
     * @param Card[] $pileOfCards
     */
    private function displayDebugInfo(Card $currentCard, array $pileOfCards)
    {
        if ($this->isDebugEnabled()) {
            echo sprintf(
                "Failed game Nr %d. Trying again... brrr (current:%d, last:%d)\n",
                static::$failedGames,
                $currentCard->number(),
                $this->lastPileCard($pileOfCards)->number()
            );
        }
    }

    private function isDebugEnabled(): bool
    {
        return static::$failedGames % self::FAILED_GAMES_DEBUG === 0;
    }
}
