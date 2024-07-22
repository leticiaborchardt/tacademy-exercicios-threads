package logprocessor;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelLogProcessor {
    private final ExecutorService executorService;

    public ParallelLogProcessor(int numberOfThreads) {
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public int processLogs(List<String> logFiles, String wordToCount) throws InterruptedException, ExecutionException {
        try {
            List<Future<Integer>> futures = logFiles.stream()
                    .map(filePath -> new LogProcessor(filePath, wordToCount))
                    .map(this.getExecutorService()::submit)
                    .toList();

            int totalOccurrences = 0;
            for (Future<Integer> future : futures) {
                totalOccurrences += future.get();
            }

            return totalOccurrences;
        } finally {
            this.getExecutorService().shutdown();
        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
