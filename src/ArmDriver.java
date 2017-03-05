import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by monique on 3/5/17.
 */
public class ArmDriver {

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the arm driver! You must set the number of slots to continue.\n" +
                "size [n] - Adjusts the number of slots, resizing if necessary. Program must start with this to be valid.\n");

        prompt();

        Arm arm = new Arm();

        // Hijinx for intellij debugging
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String input = bufferedReader.readLine();

        String[] inputs = input.split(" ");

        // todo loop until correct input
        if (inputs.length > 1 && inputs[0].equals(ValidCommands.size.toString())) {
            try {
                int size = Integer.parseInt(inputs[1]);
                arm.setSize(size);
            } catch (NumberFormatException e) {
                System.out.println(inputs[0] + " is not a valid integer");
                return;
            }
        } else {
            System.out.println("size [n] - Adjusts the number of slots, resizing if necessary. Program must start with this to be valid.");
            return;
        }

        System.out.println("Thanks! Now you can use all commands. Valid commands are:\n\n" +
                "size [n] - Adjusts the number of slots, resizing if necessary. Program must start with this to be valid.\n" +
                "add [slot] - Adds a block to the specified slot.\n" +
                "mv [slot1] [slot2] - Moves a block from slot1 to slot2.\n" +
                "rm [slot] - Removes a block from the slot.\n" +
                "replay [n] - Replays the last n commands.\n" +
                "undo [n] - Undo the last n commands.\n" +
                "quit - exit out of program\n\n");

        prompt();
        input = bufferedReader.readLine();

        inputs = input.split(" ");

        while (!inputs[0].equals(ValidCommands.quit.toString())) {
            if (ValidCommands.isValidCommand(inputs[0])) {
                arm.acceptCommandString(input,  true);
                arm.printSlots();
            } else {
                System.out.println("Invalid command");
            }

            prompt();
            input = bufferedReader.readLine();
            inputs = input.split(" ");
        }

        return;

    }

    private static void prompt() {
        System.out.print("> ");
    }
}
