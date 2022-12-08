package day7;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex2 {

    @Test
    public void ex2() throws IOException {
        Path path = Paths.get("src/test/resources/day7.txt");

        var read = Files.readAllLines(path);
        assertNotNull(read);

        Dir root = new Dir("/");

        var currentDir = root;
        for (int i = 0; i < read.size() - 1; i++) {
//            System.out.println("[" + (i + 1) + "] " + read.get(i));

            if (read.get(i).startsWith("$")) {
                var tab = read.get(i).split(" ");

                if (tab[1].equals("cd") && !tab[2].equals("..")) {
                    currentDir = currentDir.enterChildren(tab[2]);
                    if (currentDir == null) {
//                        printTree(root, 0, false);
                    }
//                    System.out.println("currentDir: " + currentDir.getName());
                } else if (tab[1].equals("cd")) {
                    currentDir = currentDir.getParentDir();
//                    System.out.println("currentDir: " + currentDir.getName());
                } else if (tab[1].equals("ls")) {
                    do {
                        i++;
                        var dirOrFileTab = read.get(i).split(" ");
                        if (dirOrFileTab[0].equals("dir")) {
                            Dir dir = new Dir(dirOrFileTab[1]);
                            currentDir.addChild(dir);
                        } else if (NumberUtils.isCreatable(dirOrFileTab[0])) {
                            currentDir.addSize(Long.parseLong(dirOrFileTab[0]));
                        }
                    } while (i + 2 <= read.size() && (read.get(i + 1).startsWith("dir") || NumberUtils.isCreatable(read.get(i + 1).substring(0, 1))));
                }
            }
        }
//        printDir(root);

        sumTreeRecursive(root);


        System.out.println("rootSize: " + root.getSize());
        var freeSpace = 70000000 - root.getSize();
        System.out.println("freeSpace: " + freeSpace);
        var requiredSpace = 30000000 - freeSpace;
        System.out.println("requiredSpace: " + requiredSpace);

        var total = allDirs.stream()
                .map(Dir::getSize)
                .filter(size -> size >= requiredSpace)
                .min(Comparator.comparing(Long::longValue))
                .get();
        System.out.println(total);
    }

    List<Dir> allDirs = new ArrayList<>();

    public void sumTreeRecursive(Dir node) {
        allDirs.add(node);
        for (Dir child : node.getChildren()) {
            sumTreeRecursive(child);
        }
    }

    public void printTree(Dir node, int depth, boolean last) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        if (last) {
            System.out.print("└");
        } else {
            System.out.print("|");
        }
        System.out.println(" " + node.getName());
        for (int i = 0; i < node.children.size() - 1; i++) {
            printTree(node.children.get(i), depth + 1, false);
        }
        if (node.children.size() > 0) {
            printTree(node.children.get(node.children.size() - 1), depth + 1, true);
        }
    }

    void printDir(Dir dir) {
        System.out.println(dir.getName() + " - " + dir.getSize());
        dir.getChildren().forEach(this::printDir);
    }

    private static class Dir {
        private String name;
        private Dir parent;
        private long size = 0;
        private List<Dir> children = new ArrayList<>();

        public Dir(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public long getSize() {
            return size + children.stream()
                    .mapToLong(Dir::getSize)
                    .sum();
        }

        public void addSize(long size) {
            this.size += size;
        }

        public List<Dir> getChildren() {
            return children;
        }

        public void addChild(Dir dir) {
            dir.setParent(this);
            if (!children.contains(dir)) {
                children.add(dir);
            }
        }

        public void setParent(Dir parent) {
            this.parent = parent;
        }

        public Dir enterChildren(String name) {
            return children.stream()
                    .filter(dir -> dir.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }

        public Dir getParentDir() {
            return this.parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dir dir = (Dir) o;
            return name.equals(dir.name);
        }
    }
}

/*
--- Day 7: No Space Left On Device ---
You can hear birds chirping and raindrops hitting leaves as the expedition proceeds.
Occasionally, you can even hear much louder sounds in the distance; how big do the animals get out here, anyway?

The device the Elves gave you has problems with more than just its communication system. You try to run a system update:

$ system-update --please --pretty-please-with-sugar-on-top
Error: No space left on device
Perhaps you can delete some files to make space for the update?

You browse around the filesystem to assess the situation and save the resulting terminal output (your puzzle input). For example:

$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
The filesystem consists of a tree of files (plain data) and directories (which can contain other directories or files).
The outermost directory is called /. You can navigate around the filesystem,
moving into or out of directories and listing the contents of the directory you're currently in.

Within the terminal output, lines that begin with $ are commands you executed, very much like some modern computers:

cd means change directory. This changes which directory is the current directory, but the specific result depends on the argument:
cd x moves in one level: it looks in the current directory for the directory named x and makes it the current directory.
cd .. moves out one level: it finds the directory that contains the current directory, then makes that directory the current directory.
cd / switches the current directory to the outermost directory, /.
ls means list. It prints out all of the files and directories immediately contained by the current directory:
123 abc means that the current directory contains a file named abc with size 123.
dir xyz means that the current directory contains a directory named xyz.
Given the commands and output in the example above, you can determine that the filesystem looks visually like this:

- / (dir)
  - a (dir)
    - e (dir)
      - i (file, size=584)
    - f (file, size=29116)
    - g (file, size=2557)
    - h.lst (file, size=62596)
  - b.txt (file, size=14848514)
  - c.dat (file, size=8504156)
  - d (dir)
    - j (file, size=4060174)
    - d.log (file, size=8033020)
    - d.ext (file, size=5626152)
    - k (file, size=7214296)
Here, there are four directories: / (the outermost directory), a and d (which are in /), and e (which is in a).
These directories also contain files of various sizes.

Since the disk is full, your first step should probably be to find directories that are good candidates for deletion.
To do this, you need to determine the total size of each directory.
The total size of a directory is the sum of the sizes of the files it contains, directly or indirectly.
(Directories themselves do not count as having any intrinsic size.)

The total sizes of the directories above can be found as follows:

The total size of directory e is 584 because it contains a single file i of size 584 and no other directories.
The directory a has total size 94853 because it contains files f (size 29116), g (size 2557), and h.lst (size 62596),
plus file i indirectly (a contains e which contains i).
Directory d has total size 24933642.
As the outermost directory, / contains every file. Its total size is 48381165, the sum of the size of every file.
To begin, find all of the directories with a total size of at most 100000, then calculate the sum of their total sizes.
In the example above, these directories are a and e; the sum of their total sizes is 95437 (94853 + 584).
(As in this example, this process can count files more than once!)

Find all of the directories with a total size of at most 100000. What is the sum of the total sizes of those directories?
 */
