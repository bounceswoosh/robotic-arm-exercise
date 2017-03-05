/**
 * Created by monique on 3/5/17.
 */
public enum ValidCommands {
    size("size"),
    add("add"),
    mv("mv"),
    rm("rm"),
    replay("replay"),
    undo("undo"),
    quit("quit");

    private final String command;

    ValidCommands(final String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }

    public static boolean isValidCommand(String command) {
        try {
            ValidCommands test = ValidCommands.valueOf(command);
            return true;
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }
}
