import java.util.List;

/**
 * Created by monique on 3/5/17.
 */
public class Command {

    ValidCommands command;
    Block block;
    List<Integer> parameters;

    public Command(ValidCommands command, Block block, List<Integer> parameters) {
        this.command = command;
        this.block = block;
        this.parameters = parameters;
    }

    public ValidCommands getCommand() {
        return command;
    }

    public Block getBlock() {
        return block;
    }

    public List<Integer> getParameters() {
        return parameters;
    }
}
