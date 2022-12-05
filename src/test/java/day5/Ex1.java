package day5;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex1 {

    Stack<Character> stack1 = new Stack<>();
    Stack<Character> stack2 = new Stack<>();
    Stack<Character> stack3 = new Stack<>();
    Stack<Character> stack4 = new Stack<>();
    Stack<Character> stack5 = new Stack<>();
    Stack<Character> stack6 = new Stack<>();
    Stack<Character> stack7 = new Stack<>();
    Stack<Character> stack8 = new Stack<>();
    Stack<Character> stack9 = new Stack<>();

    Stack<Character>[] stackArray = new Stack[9];

    void init() {
        stack1.push('N');
        stack1.push('B');
        stack1.push('D');
        stack1.push('T');
        stack1.push('V');
        stack1.push('G');
        stack1.push('Z');
        stack1.push('J');

        stack2.push('S');
        stack2.push('R');
        stack2.push('M');
        stack2.push('D');
        stack2.push('W');
        stack2.push('P');
        stack2.push('F');

        stack3.push('V');
        stack3.push('C');
        stack3.push('R');
        stack3.push('S');
        stack3.push('Z');

        stack4.push('R');
        stack4.push('T');
        stack4.push('J');
        stack4.push('Z');
        stack4.push('P');
        stack4.push('H');
        stack4.push('G');

        stack5.push('T');
        stack5.push('C');
        stack5.push('J');
        stack5.push('N');
        stack5.push('D');
        stack5.push('Z');
        stack5.push('Q');
        stack5.push('F');

        stack6.push('N');
        stack6.push('V');
        stack6.push('P');
        stack6.push('W');
        stack6.push('G');
        stack6.push('S');
        stack6.push('F');
        stack6.push('M');

        stack7.push('G');
        stack7.push('C');
        stack7.push('V');
        stack7.push('B');
        stack7.push('P');
        stack7.push('Q');

        stack8.push('Z');
        stack8.push('B');
        stack8.push('P');
        stack8.push('N');

        stack9.push('W');
        stack9.push('P');
        stack9.push('J');

        stackArray[0] = stack1;
        stackArray[1] = stack2;
        stackArray[2] = stack3;
        stackArray[3] = stack4;
        stackArray[4] = stack5;
        stackArray[5] = stack6;
        stackArray[6] = stack7;
        stackArray[7] = stack8;
        stackArray[8] = stack9;
    }


    @Test
    public void ex1() throws IOException {
        init();
        Path path = Paths.get("src/test/resources/day5-v2.txt");

        var read = Files.readAllLines(path);
        assertNotNull(read);

        for (String s : read) {
            var tab = s.split(" ");
            int howMany = Integer.parseInt(tab[0]);
            int from = Integer.parseInt(tab[1]);
            int to = Integer.parseInt(tab[2]);

            for (int i = 0; i < howMany; i++) {
                stackArray[to - 1].push(stackArray[from - 1].pop());
            }
        }

//        for (int i = 0; i < 9; i++) {
//            System.out.println("Stack " + (i + 1) + ": " + stackArray[i].pop());
//        }
        for (int i = 0; i < 9; i++) {
            System.out.print(stackArray[i].pop());
        }
    }
}

/*
--- Day 5: Supply Stacks ---
The expedition can depart as soon as the final supplies have been unloaded from the ships.
Supplies are stored in stacks of marked crates, but because the needed supplies are buried under many other crates, the crates need to be rearranged.

The ship has a giant cargo crane capable of moving crates between stacks.
To ensure none of the crates get crushed or fall over, the crane operator will rearrange them in a series of carefully-planned steps.
 After the crates are rearranged, the desired crates will be at the top of each stack.

The Elves don't want to interrupt the crane operator during this delicate procedure,
but they forgot to ask her which crate will end up where, and they want to be ready to unload them as soon as possible so they can embark.

They do, however, have a drawing of the starting stacks of crates and the rearrangement procedure (your puzzle input).
For example:

    [D]
[N] [C]
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
In this example, there are three stacks of crates. Stack 1 contains two crates: crate Z is on the bottom, and crate N is on top.
Stack 2 contains three crates; from bottom to top, they are crates M, C, and D. Finally, stack 3 contains a single crate, P.

Then, the rearrangement procedure is given. In each step of the procedure, a quantity of crates is moved from one stack to a different stack.
In the first step of the above rearrangement procedure, one crate is moved from stack 2 to stack 1, resulting in this configuration:

[D]
[N] [C]
[Z] [M] [P]
 1   2   3
In the second step, three crates are moved from stack 1 to stack 3. Crates are moved one at a time,
so the first crate to be moved (D) ends up below the second and third crates:

        [Z]
        [N]
    [C] [D]
    [M] [P]
 1   2   3
Then, both crates are moved from stack 2 to stack 1. Again, because crates are moved one at a time,
crate C ends up below crate M:

        [Z]
        [N]
[M]     [D]
[C]     [P]
 1   2   3
Finally, one crate is moved from stack 1 to stack 2:

        [Z]
        [N]
        [D]
[C] [M] [P]
 1   2   3
The Elves just need to know which crate will end up on top of each stack; in this example,
the top crates are C in stack 1, M in stack 2, and Z in stack 3, so you should combine these together and give the Elves the message CMZ.

After the rearrangement procedure completes, what crate ends up on top of each stack?
 */