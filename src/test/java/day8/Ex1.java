package day8;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex1 {

    @Test
    public void ex1() throws IOException {
        Path path = Paths.get("src/test/resources/day8.txt");

        var read = Files.readAllLines(path);
        assertNotNull(read);
        int xSize = read.size();
        int ySize = read.get(0).length();
        List<Tree> trees = new ArrayList<>(xSize * ySize);
        for (int i = 0; i < read.size(); i++) {
            var tab = read.get(i).chars().mapToObj(Character::getNumericValue).toList();
            for (int j = 0; j < tab.size(); j++) {
                trees.add(new Tree(tab.get(j), i, j));
            }
        }
        int counter = 0;
        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            if (tree.getY() == 0 || tree.getX() == 0 || tree.getX() == 98 || tree.getY() == 98) {
                counter++;
            } else if (isTreeVisible(tree, trees)) {
                counter++;
            }
        }
        System.out.println(counter);

    }


    private boolean isTreeVisible(Tree tree, List<Tree> trees) {
        return trees.stream()
                .filter(t -> t.getY() == tree.getY() && t.getX() < tree.getX())
                .allMatch(t -> t.getHeight() < tree.getHeight())
                || trees.stream()
                .filter(t -> t.getY() == tree.getY() && t.getX() > tree.getX())
                .allMatch(t -> t.getHeight() < tree.getHeight())
                || trees.stream()
                .filter(t -> t.getX() == tree.getX() && t.getY() < tree.getY())
                .allMatch(t -> t.getHeight() < tree.getHeight())
                || trees.stream()
                .filter(t -> t.getX() == tree.getX() && t.getY() > tree.getY())
                .allMatch(t -> t.getHeight() < tree.getHeight());
    }

    private List<Tree> getTreesByXAndHeight(int x, int height, List<Tree> trees) {
        List<Tree> result = new ArrayList<>();
        for (Tree tree : trees) {
            if (tree.getX() == x && tree.getHeight() == height) {
                result.add(tree);
            }
        }
        return result;
    }
}

class Tree {
    private final int height;
    private final int x;
    private final int y;

    public Tree(int height, int x, int y) {
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

/*
--- Day 8: Treetop Tree House ---
The expedition comes across a peculiar patch of tall trees all planted carefully in a grid.
The Elves explain that a previous expedition planted these trees as a reforestation effort. Now, they're curious if this would be a good location for a tree house.

First, determine whether there is enough tree cover here to keep a tree house hidden.
To do this, you need to count the number of trees that are visible from outside the grid when looking directly
along a row or column.

The Elves have already launched a quadcopter to generate a map with the height of each tree (your puzzle input). For example:

30373
25512
65332
33549
35390
Each tree is represented as a single digit whose value is its height, where 0 is the shortest and 9 is the tallest.

A tree is visible if all of the other trees between it and an edge of the grid are shorter than it.
Only consider trees in the same row or column; that is, only look up, down, left, or right from any given tree.

All of the trees around the edge of the grid are visible - since they are already on the edge,
there are no trees to block the view. In this example, that only leaves the interior nine trees to consider:

The top-left 5 is visible from the left and top. (It isn't visible from the right or bottom since other
 trees of height 5 are in the way.)
The top-middle 5 is visible from the top and right.
The top-right 1 is not visible from any direction; for it to be visible, there would need to only be trees of
height 0 between it and an edge.
The left-middle 5 is visible, but only from the right.
The center 3 is not visible from any direction; for it to be visible, there would need to be only trees of at
most height 2 between it and an edge.
The right-middle 3 is visible from the right.
In the bottom row, the middle 5 is visible, but the 3 and 4 are not.
With 16 trees visible on the edge and another 5 visible in the interior, a total of 21 trees are visible in this arrangement.

Consider your map; how many trees are visible from outside the grid?
 */