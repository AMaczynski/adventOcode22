package day6;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex2 {

    @Test
    public void ex2() throws IOException {
        Path path = Paths.get("src/test/resources/day6.txt");

        var read = Files.readAllLines(path);
        assertNotNull(read);
        var tab = read.get(0).chars().mapToObj(e -> (char) e).toList();

        for (int i = 0; i < tab.size() - 14; i++) {
            var distinctElementsCount = Stream.of(tab.get(i), tab.get(i + 1), tab.get(i + 2), tab.get(i + 3),
                            tab.get(i + 4), tab.get(i + 5), tab.get(i + 6), tab.get(i + 7), tab.get(i + 8),
                            tab.get(i + 9), tab.get(i + 10), tab.get(i + 11), tab.get(i + 12), tab.get(i + 13))
                    .distinct()
                    .count();
            if (distinctElementsCount == 14) {
                System.out.println("result: " + (i + 14));
            }

        }
    }
}

/*
--- Part Two ---
Your device's communication system is correctly detecting packets, but still isn't working. It looks like it also needs to look for messages.

A start-of-message marker is just like a start-of-packet marker, except it consists of 14 distinct characters rather than 4.

Here are the first positions of start-of-message markers for all of the above examples:

mjqjpqmgbljsphdztnvjfqwrcgsmlb: first marker after character 19
bvwbjplbgvbhsrlpgdmjqwftvncz: first marker after character 23
nppdvjthqldpwncqszvftbrmjlhg: first marker after character 23
nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg: first marker after character 29
zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw: first marker after character 26
How many characters need to be processed before the first start-of-message marker is detected?
 */
