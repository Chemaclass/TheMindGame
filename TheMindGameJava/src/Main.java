import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final int ONE_SECOND_IN_NANO = 1_000_000_000;

    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();

        int numPlayers = 2, numLevelsToWin = 3;
        int totalThreads = Runtime.getRuntime().availableProcessors();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < totalThreads; i++) {
            TheMindGame game = new TheMindGame(numPlayers, numLevelsToWin);
            Thread thread = new Thread(game);
            thread.start();
            threads.add(thread);
        }

        for (Thread t: threads){
            t.join();
        }

        Instant end = Instant.now();
        int nano = Duration.between(start, end).getNano();
        double elapsedTimeInSecond = (double) nano / ONE_SECOND_IN_NANO;

        System.out.printf("Total time in seconds: %f\n", elapsedTimeInSecond);
    }
}
