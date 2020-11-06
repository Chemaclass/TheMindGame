import java.util.List;
import java.util.Map;

public class TheMindGameResult {
    private final int failedGames;
    private final Map<Integer, List<Card>> result;

    public TheMindGameResult(int failedGames, Map<Integer, List<Card>> result) {
        this.failedGames = failedGames;
        this.result = result;
    }

    public int getFailedGames() {
        return failedGames;
    }

    public Map<Integer, List<Card>> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "failedGames=" + failedGames +
                ", " + result;
    }
}
