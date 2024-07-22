package logprocessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record LogProcessor(String logFilePath, String wordSearch) implements Callable<Integer> {
    public LogProcessor(String logFilePath, String wordSearch) {
        this.logFilePath = logFilePath;
        this.wordSearch = wordSearch.toLowerCase();
    }

    @Override
    public Integer call() {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(this.logFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                count += this.countOccurrences(line, this.wordSearch);
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível ler o arquivo de log: " + this.logFilePath, e);
        }

        return count;
    }

    private int countOccurrences(String line, String word) {
        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(word.toLowerCase()) + "\\b");
        Matcher matcher = pattern.matcher(line.toLowerCase());

        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }
}
