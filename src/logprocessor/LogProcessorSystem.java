package logprocessor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LogProcessorSystem {
    public static void run() {
        List<String> logFiles = Arrays.asList(
                "src/logprocessor/logfiles/log-01.txt",
                "src/logprocessor/logfiles/log-02.txt",
                "src/logprocessor/logfiles/log-03.txt"
        );

        String wordSearch = "Error";

        try {
            ParallelLogProcessor processor = new ParallelLogProcessor(5);

            int totalOccurrences = processor.processLogs(logFiles, wordSearch);

            System.out.println("Total de ocorrências encontradas de \"" + wordSearch + "\": " + totalOccurrences);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
