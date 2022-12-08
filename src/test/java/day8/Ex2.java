package day8;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex2 {

    @Test
    public void ex2() throws IOException {
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

        var score = trees.stream()
                .map(t -> countScoreForTree(t, trees))
                .max(Integer::compareTo);
        System.out.println(score);
    }

    private int countScoreForTree(Tree tree, List<Tree> treeList) {
        // score top
        var topScore = 0;
        var topMap = treeList.stream()
                .filter(t -> t.getY() == tree.getY() && t.getX() > tree.getX())
                .toList();
        for (int i = 0; i < topMap.size(); i++) {
            var tmpTree = topMap.get(i);
            topScore++;
            if (tmpTree.getHeight() >= tree.getHeight()) {
                break;
            }
        }
        // score bottom
        var bottomScore = 0;
        var bottomMap = treeList.stream()
                .filter(t -> t.getY() == tree.getY() && t.getX() < tree.getX())
                .toList();
        for (int i = bottomMap.size() - 1; i >= 0; i--) {
            var tmpTree = bottomMap.get(i);
            bottomScore++;
            if (tmpTree.getHeight() >= tree.getHeight()) {
                break;
            }
        }
        // score left
        var leftScore = 0;
        var leftMap = treeList.stream()
                .filter(t -> t.getX() == tree.getX() && t.getY() < tree.getY())
                .toList();
        for (int i = leftMap.size() - 1; i >= 0; i--) {
            var tmpTree = leftMap.get(i);
            leftScore++;
            if (tmpTree.getHeight() >= tree.getHeight()) {
                break;
            }
        }
        // score right
        var rightScore = 0;
        var rightMap = treeList.stream()
                .filter(t -> t.getX() == tree.getX() && t.getY() > tree.getY())
                .toList();
        for (int i = 0; i < rightMap.size(); i++) {
            var tmpTree = rightMap.get(i);
            rightScore++;
            if (tmpTree.getHeight() >= tree.getHeight()) {
                break;
            }
        }
//        System.out.println(topScore * bottomScore * leftScore * rightScore);
        return topScore * bottomScore * leftScore * rightScore;
    }
}

/*
--- Part Two ---
Content with the amount of tree cover available, the Elves just need to know the best spot to build their tree house:
 they would like to be able to see a lot of trees.

To measure the viewing distance from a given tree, look up, down, left, and right from that tree;
stop if you reach an edge or at the first tree that is the same height or taller than the tree under consideration.
(If a tree is right on the edge, at least one of its viewing distances will be zero.)

The Elves don't care about distant trees taller than those found by the rules above; the proposed tree house has
large eaves to keep it dry, so they wouldn't be able to see higher than the tree house anyway.

In the example above, consider the middle 5 in the second row:

30373
25512
65332
33549
35390
Looking up, its view is not blocked; it can see 1 tree (of height 3).
Looking left, its view is blocked immediately; it can see only 1 tree (of height 5, right next to it).
Looking right, its view is not blocked; it can see 2 trees.
Looking down, its view is blocked eventually; it can see 2 trees (one of height 3, then the tree of height 5 that blocks its view).
A tree's scenic score is found by multiplying together its viewing distance in each of the four directions.
For this tree, this is 4 (found by multiplying 1 * 1 * 2 * 2).

However, you can do even better: consider the tree of height 5 in the middle of the fourth row:

30373
25512
65332
33549
35390
Looking up, its view is blocked at 2 trees (by another tree with a height of 5).
Looking left, its view is not blocked; it can see 2 trees.
Looking down, its view is also not blocked; it can see 1 tree.
Looking right, its view is blocked at 2 trees (by a massive tree of height 9).
This tree's scenic score is 8 (2 * 2 * 1 * 2); this is the ideal spot for the tree house.

Consider each tree on your map. What is the highest scenic score possible for any tree?
 */