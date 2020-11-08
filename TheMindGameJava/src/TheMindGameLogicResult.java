import ReadModel.Card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheMindGameLogicResult {
    private final int failedGames;
    private final Map<Integer, List<Card>> result;

    public static TheMindGameLogicResult createEmpty() {
        return new TheMindGameLogicResult(0, new HashMap<>());
    }

    public TheMindGameLogicResult(int failedGames, Map<Integer, List<Card>> result) {
        this.failedGames = failedGames;
        this.result = result;
    }

    @Override
    public String toString() {
        return "failedGames=" + failedGames + ", " + result;
    }

    public boolean isEmpty() {
        return result.isEmpty();
    }
}
