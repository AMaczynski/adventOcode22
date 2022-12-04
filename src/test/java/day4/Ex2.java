package day4;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static day4.Ex1.generateRangeList;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex2 {

    @Test
    public void ex1() throws IOException {
        Path path = Paths.get("src/test/resources/day4.txt");
        var read = Files.readAllLines(path);
        assertNotNull(read);

        var score = 0;
        for (var line : read) {
            var t = line.split(",");
            var range1 = generateRangeList(t[0]);
            var range2 = generateRangeList(t[1]);
            if (overlap(range1, range2)) {
                score++;
            }
        }
        System.out.println(score);

    }

    private boolean overlap(List<Long> range1, List<Long> range2) {
        return !Collections.disjoint(range1, range2);
    }

}

/*
--- Part Two ---
It seems like there is still quite a bit of duplicate work planned.
Instead, the Elves would like to know the number of pairs that overlap at all.

In the above example, the first two pairs (2-4,6-8 and 2-3,4-5) don't overlap,
while the remaining four pairs (5-7,7-9, 2-8,3-7, 6-6,4-6, and 2-6,4-8) do overlap:

5-7,7-9 overlaps in a single section, 7.
2-8,3-7 overlaps all of the sections 3 through 7.
6-6,4-6 overlaps in a single section, 6.
2-6,4-8 overlaps in sections 4, 5, and 6.
So, in this example, the number of overlapping assignment pairs is 4.

In how many assignment pairs do the ranges overlap?
 */
