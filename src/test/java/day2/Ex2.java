package day2;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex2 {

    private static final Map<String, String> winningMap = new HashMap<>();
    private static final Map<String, String> loseMap = new HashMap<>();
    private static final Map<String, Long> valueMap = new HashMap<>();
    // A - rock
    // B - paper
    // C - scissors

    // X - lose,
    // Y - draw
    // Z - win

    static {
        winningMap.put("A", "B");
        winningMap.put("B", "C");
        winningMap.put("C", "A");

        loseMap.put("A", "C");
        loseMap.put("B", "A");
        loseMap.put("C", "B");

        valueMap.put("A", 1L);
        valueMap.put("B", 2L);
        valueMap.put("C", 3L);
    }

    @Test
    public void ex2() throws IOException {
        Path path = Paths.get("src/test/resources/day2.txt");

        var read = Files.readAllLines(path);
        assertNotNull(read);

        long result = 0;

        for(String line: read) {
            String[] signTab = line.split(" ");
            var opponent = signTab[0];
            var score = signTab[1];

            if (score.equals("X")) {
                var mine = loseMap.get(opponent);
                result += valueMap.get(mine);
            } else if (score.equals("Y")) {
                result += valueMap.get(opponent);
                result += 3;
            } else if (score.equals("Z")) {
                var mine = winningMap.get(opponent);
                result += valueMap.get(mine);
                result += 6;
            }
        }
        System.out.println(result);
    }

}
