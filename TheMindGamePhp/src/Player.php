<?php

declare(strict_types=1);

namespace App;

final class Player
{
    private const MIN_VALUE = 1;
    private const MAX_VALUE = 100;

    /**
     * @var Card[]
     */
    private array $cards;

    /**
     * A player consist of a list of (already-sorted) cards
     */
    public static function create(int $currentLevel, Player...$existingPlayers): self
    {
        $cards = [];

        $allNumbers = array_map(
            static fn(Card $c): int => $c->number(),
            array_merge(...array_map(
                static fn(Player $pc): array => $pc->cards,
                $existingPlayers
            ))
        );

        for ($i = 0; $i < $currentLevel; $i++) {
            do {
                $cardNumber = random_int(self::MIN_VALUE, self::MAX_VALUE);
            } while (in_array($cardNumber, $allNumbers));

            $allNumbers[] = $cardNumber;
            $cards[] = new Card($cardNumber);
        }

        usort($cards, static fn(Card $c1, Card $c2) => $c2->number() <=> $c1->number());

        return new self($cards);
    }

    private function __construct(array $cards)
    {
        $this->cards = $cards;
    }

    public function popMinCard(): Card
    {
        return array_pop($this->cards);
    }

    public function totalCards(): int
    {
        return count($this->cards);
    }
}
