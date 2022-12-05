package day5;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Ex2 {

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
            var tmpStack = new Stack<Character>();
            var tab = s.split(" ");
            int howMany = Integer.parseInt(tab[0]);
            int from = Integer.parseInt(tab[1]);
            int to = Integer.parseInt(tab[2]);

            for (int i = 0; i < howMany; i++) {
                tmpStack.push(stackArray[from - 1].pop());
            }

            for (int i = 0; i < howMany; i++) {
                stackArray[to - 1].push(tmpStack.pop());
            }


        }
        for (int i = 0; i < 9; i++) {
            System.out.print(stackArray[i].pop());
        }
    }

}

/*
--- Part Two ---
As you watch the crane operator expertly rearrange the crates, you notice the process isn't following your prediction.

Some mud was covering the writing on the side of the crane, and you quickly wipe it away.
The crane isn't a CrateMover 9000 - it's a CrateMover 9001.

The CrateMover 9001 is notable for many new and exciting features: air conditioning, leather seats, an extra cup holder,
and the ability to pick up and move multiple crates at once.

Again considering the example above, the crates begin in the same configuration:

    [D]
[N] [C]
[Z] [M] [P]
 1   2   3
Moving a single crate from stack 2 to stack 1 behaves the same as before:

[D]
[N] [C]
[Z] [M] [P]
 1   2   3
However, the action of moving three crates from stack 1 to stack 3 means that those three moved crates stay in the same order,
resulting in this new configuration:

        [D]
        [N]
    [C] [Z]
    [M] [P]
 1   2   3
Next, as both crates are moved from stack 2 to stack 1, they retain their order as well:

        [D]
        [N]
[C]     [Z]
[M]     [P]
 1   2   3
Finally, a single crate is still moved from stack 1 to stack 2, but now it's crate C that gets moved:

        [D]
        [N]
        [Z]
[M] [C] [P]
 1   2   3
In this example, the CrateMover 9001 has put the crates in a totally different order: MCD.

Before the rearrangement process finishes, update your simulation so that the Elves know where they
should stand to be ready to unload the final supplies. After the rearrangement procedure completes,
what crate ends up on top of each stack?
 */
