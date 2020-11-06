<?php

declare(strict_types=1);

namespace App;

final class PlayerCards
{
    private const MIN_VALUE = 1;
    private const MAX_VALUE = 150;

    /**
     * @var Card[]
     */
    private array $cards;

    public static function create(int $currentLevel, PlayerCards...$existingPlayerCards): self
    {
        $cards = [];
        $allNumbers = array_map(
            static fn(Card $c): int => $c->number(),
            array_merge(...array_map(
                static fn(PlayerCards $pc): array => $pc->cards,
                $existingPlayerCards
            ))
        );
//dump($allNumbers);
        for ($i = 0; $i < $currentLevel; $i++) {
            do {
                $cardNumber = random_int(self::MIN_VALUE, self::MAX_VALUE);
            } while (in_array($cardNumber, $allNumbers));

            $allNumbers[] = $cardNumber;
            $cards[] = new Card($cardNumber);
        }

//        dd($allNumbers);
        return new self($cards);
    }

    private function __construct(array $cards)
    {
        $this->cards = $cards;
    }

    public function popMinCard(): Card
    {
        $minCard = min(array_map(
            static fn(Card $c): int => $c->number(),
            $this->cards
        ));

        $this->cards = array_diff($this->cards, [new Card($minCard)]);

        return new Card($minCard);
    }

    public function totalCards(): int
    {
        return count($this->cards);
    }
}
