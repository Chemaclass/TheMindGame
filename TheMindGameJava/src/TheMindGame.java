
public class TheMindGame implements Runnable {
    private final int numPlayers;
    private final int numLevelsToWin;

    public TheMindGame(int numPlayers, int numLevelsToWin) {
        this.numPlayers = numPlayers;
        this.numLevelsToWin = numLevelsToWin;
    }

    @Override
    public void run() {
        System.out.println("Inside : " + Thread.currentThread().getName());

        TheMindGameLogic logic = new TheMindGameLogic(numPlayers, numLevelsToWin);
        TheMindGameLogicResult result = logic.play();
        TheMindGameLogic.stopRunningThreads();

        if (!result.isEmpty()) {
            System.out.println(result);
        }
    }
}
