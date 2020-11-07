<?php

declare(strict_types=1);

namespace App;

final class TheMindGameResult
{
    private int $failedGames;
    private array $result;

    public static function create(int $failedGames, array $result): self
    {
        return new self($failedGames, $result);
    }

    public static function empty(): self
    {
        return new self(0, []);
    }

    private function __construct(int $failedGames, array $result)
    {
        $this->failedGames = $failedGames;
        $this->result = $result;
    }

    public function failedGames(): int
    {
        return $this->failedGames;
    }

    public function result(): array
    {
        return $this->result;
    }
}
