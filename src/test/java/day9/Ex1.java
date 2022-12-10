package day9;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex1 {

    @Test
    public void ex1() throws IOException {
        Path path = Paths.get("src/test/resources/day9.txt");

        var read = Files.readAllLines(path);
        assertNotNull(read);

        Set<Point> visitedByTail = new HashSet<>();
        var startPoint = new Point(0, 0);
        visitedByTail.add(startPoint);
        var headX = 0;
        var headY = 0;
        var tailX = 0;
        var tailY = 0;
        for (var r : read) {
            var split = r.split(" ");
            var direction = split[0];
            var steps = Integer.parseInt(split[1]);

            switch (direction) {
                case "L" -> {
                    for (int i = 0; i < steps; i++) {
                        headX -= 1;
                        var tailPoint = countTailPosition(headX, headY, tailX, tailY);
                        tailX = tailPoint.x;
                        tailY = tailPoint.y;
                        visitedByTail.add(tailPoint);
                        printPositions(headX, headY, tailX, tailY);
                    }
                }
                case "R" -> {
                    for (int i = 0; i < steps; i++) {
                        headX += 1;
                        var tailPoint = countTailPosition(headX, headY, tailX, tailY);
                        tailX = tailPoint.x;
                        tailY = tailPoint.y;
                        visitedByTail.add(tailPoint);
                        printPositions(headX, headY, tailX, tailY);
                    }
                }
                case "U" -> {
                    for (int i = 0; i < steps; i++) {
                        headY += 1;
                        var tailPoint = countTailPosition(headX, headY, tailX, tailY);
                        tailX = tailPoint.x;
                        tailY = tailPoint.y;
                        visitedByTail.add(tailPoint);
                        printPositions(headX, headY, tailX, tailY);
                    }
                }
                case "D" -> {
                    for (int i = 0; i < steps; i++) {
                        headY -= 1;
                        var tailPoint = countTailPosition(headX, headY, tailX, tailY);
                        tailX = tailPoint.x;
                        tailY = tailPoint.y;
                        visitedByTail.add(tailPoint);
                        printPositions(headX, headY, tailX, tailY);
                    }
                }
            }

        }
        System.out.println(visitedByTail.stream().distinct().toList().size());
    }

    static void printPositions(int headX, int headY, int tailX, int tailY) {
        System.out.println("Head: " + headX + " " + headY);
        System.out.println("Tail: " + tailX + " " + tailY);
    }

    static Point countTailPosition(int headX, int headY, int tailX, int tailY) {
        if (headX - tailX > 1 && headY == tailY) {
            return new Point(tailX + 1, tailY);
        } else if (headX - tailX < -1 && headY == tailY) {
            return new Point(tailX - 1, tailY);
        } else if (headY - tailY > 1 && headX == tailX) {
            return new Point(tailX, tailY + 1);
        } else if (headY - tailY < -1 && headX == tailX) {
            return new Point(tailX, tailY - 1);
        } else if (headX - tailX > 1 && headY > tailY) {
            return new Point(tailX + 1, tailY + 1);
        } else if (headX - tailX > 1 && headY < tailY) {
            return new Point(tailX + 1, tailY - 1);
        } else if (headX - tailX < -1 && headY > tailY) {
            return new Point(tailX - 1, tailY + 1);
        } else if (headX - tailX < -1 && headY < tailY) {
            return new Point(tailX - 1, tailY - 1);
        } else if (headY - tailY > 1 && headX > tailX) {
            return new Point(tailX + 1, tailY + 1);
        } else if (headY - tailY > 1 && headX < tailX) {
            return new Point(tailX - 1, tailY + 1);
        } else if (headY - tailY < -1 && headX > tailX) {
            return new Point(tailX + 1, tailY - 1);
        } else if (headY - tailY < -1 && headX < tailX) {
            return new Point(tailX - 1, tailY - 1);
        } else {
            return new Point(tailX, tailY);
        }
    }

    static class Point {
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (x != point.x) return false;
            return y == point.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

}

/*
--- Day 9: Rope Bridge ---
This rope bridge creaks as you walk along it. You aren't sure how old it is, or whether it can even support your weight.

It seems to support the Elves just fine, though. The bridge spans a gorge which was carved out by the massive river far below you.

You step carefully; as you do, the ropes stretch and twist. You decide to distract yourself by modeling rope physics;
maybe you can even figure out where not to step.

Consider a rope with a knot at each end; these knots mark the head and the tail of the rope.
If the head moves far enough away from the tail, the tail is pulled toward the head.

Due to nebulous reasoning involving Planck lengths, you should be able to model the positions of the knots on a two-dimensional grid.
Then, by following a hypothetical series of motions (your puzzle input) for the head, you can determine how the tail will move.

Due to the aforementioned Planck lengths, the rope must be quite short; in fact,
the head (H) and tail (T) must always be touching (diagonally adjacent and even overlapping both count as touching):

....
.TH.
....

....
.H..
..T.
....

...
.H. (H covers T)
...
If the head is ever two steps directly up, down, left, or right from the tail,
the tail must also move one step in that direction so it remains close enough:

.....    .....    .....
.TH.. -> .T.H. -> ..TH.
.....    .....    .....

...    ...    ...
.T.    .T.    ...
.H. -> ... -> .T.
...    .H.    .H.
...    ...    ...
Otherwise, if the head and tail aren't touching and aren't in the same row or column,
the tail always moves one step diagonally to keep up:

.....    .....    .....
.....    ..H..    ..H..
..H.. -> ..... -> ..T..
.T...    .T...    .....
.....    .....    .....

.....    .....    .....
.....    .....    .....
..H.. -> ...H. -> ..TH.
.T...    .T...    .....
.....    .....    .....
You just need to work out where the tail goes as the head follows a series of motions.
Assume the head and the tail both start at the same position, overlapping.

For example:

R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
This series of motions moves the head right four steps, then up four steps, then left three steps, then down one step,
 and so on. After each step, you'll need to update the position of the tail if the step means the head is no longer adjacent to the tail.
 Visually, these motions occur as follows (s marks the starting position as a reference point):

== Initial State ==

......
......
......
......
H.....  (H covers T, s)

== R 4 ==

......
......
......
......
TH....  (T covers s)

......
......
......
......
sTH...

......
......
......
......
s.TH..

......
......
......
......
s..TH.

== U 4 ==

......
......
......
....H.
s..T..

......
......
....H.
....T.
s.....

......
....H.
....T.
......
s.....

....H.
....T.
......
......
s.....

== L 3 ==

...H..
....T.
......
......
s.....

..HT..
......
......
......
s.....

.HT...
......
......
......
s.....

== D 1 ==

..T...
.H....
......
......
s.....

== R 4 ==

..T...
..H...
......
......
s.....

..T...
...H..
......
......
s.....

......
...TH.
......
......
s.....

......
....TH
......
......
s.....

== D 1 ==

......
....T.
.....H
......
s.....

== L 5 ==

......
....T.
....H.
......
s.....

......
....T.
...H..
......
s.....

......
......
..HT..
......
s.....

......
......
.HT...
......
s.....

......
......
HT....
......
s.....

== R 2 ==

......
......
.H....  (H covers T)
......
s.....

......
......
.TH...
......
s.....
After simulating the rope, you can count up all of the positions the tail visited at least once.
In this diagram, s again marks the starting position (which the tail also visited) and # marks other positions the tail visited:

..##..
...##.
.####.
....#.
s###..
So, there are 13 positions the tail visited at least once.

Simulate your complete hypothetical series of motions. How many positions does the tail of the rope visit at least once?
 */