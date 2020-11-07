<?php

declare(strict_types=1);

namespace App;

final class Card
{
    private int $number;

    public function __construct(int $number)
    {
        $this->number = $number;
    }

    public function number(): int
    {
        return $this->number;
    }

    public function __toString(): string
    {
        return (string)$this->number;
    }
}
