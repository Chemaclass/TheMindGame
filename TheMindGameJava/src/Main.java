
public class Main {

    public static void main(String[] args) {
        int numPlayers = 2, numLevelsToWin = 4;

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            TheMindGame game = new TheMindGame(numPlayers, numLevelsToWin);
            Thread thread = new Thread(game);
            thread.start();
        }
    }
}
