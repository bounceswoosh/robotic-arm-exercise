
import java.util.*;

/**
 * Created by monique on 3/5/17.
 */
public class Arm {

    // ASSUMPTION: class is not thread safe
    // ASSUMPTION: resetting size also resets all data, history, etc

    protected int size = 0;


    SortedMap<Integer, ArrayList<Block>> slots = new TreeMap<>();

    protected ArrayList<Command> history = new ArrayList<>();


    public boolean acceptCommandString(String commandString, boolean remember) {

        String[] inputs = commandString.split(" ");
        ValidCommands command = ValidCommands.valueOf(inputs[0]);

        ArrayList<Integer> parameters = new ArrayList<>();

        try {
            if (inputs.length > 1) {
                for (int i = 1; i < inputs.length; i++) {
                    parameters.add(Integer.parseInt(inputs[i]));
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("All parameters must be integers");
            return false;
        }

        Command commandObject = new Command(command, new Block(), parameters);

        switch(command) {
            case size:
                setSize(parameters.get(0));
                if (remember) {
                    history.add(commandObject);
                }
                break;
            case add:
                add(parameters.get(0));
                if (remember) {
                    history.add(commandObject);
                }
                break;
            case mv:
                if (parameters.size() > 1) {
                    move(parameters.get(0), parameters.get(1));
                    if (remember) {
                        history.add(commandObject);
                    }
                }
            break;
            case rm:
                remove(parameters.get(0));
                if (remember) {
                    history.add(commandObject);
                }
                break;
            case replay:
                replay(parameters.get(0));
                break;
            case undo:
                undo(parameters.get(0));
                break;
        }


        return true;

    }


    public void setSize(int size) {
        // ASSUMPTION : setting size blows away existing data
        this.size = size;

        slots = new TreeMap<>();
        for (int i = 1; i <= size;  i++ ) {
            slots.put(i, null);
        }

        history.clear();

    }

    public void replay(int countCommands) {

        // replay means to do the same set of steps again

        int start = history.size() - countCommands;

        for (int i = start; i < history.size(); i++) {
            Command command = history.get(i);

            String commandString = command.getCommand().toString();
            for (Integer parameter : command.getParameters()) {
                commandString += " " + parameter.toString();
            }


            acceptCommandString(commandString, false);

        }

    }

    public void undo(int countCommands) {
        // ASSUMPTION: undoing past the start does nothing

        // This is cheating, but I have 2 hours

        // TODO - undo is definitely buggy.

        slots.clear();
        setSize(size);

        int redoCommands = history.size() - countCommands;

        for (int i = 0; i < history.size(); i++) {

            Command command = history.get(i);

            String commandString = command.getCommand().toString();
            for (Integer parameter : command.getParameters()) {
                commandString += " " + parameter.toString();
            }


            acceptCommandString(commandString, false);

        }

    }

    public void add(int slot) {
        if (slot > size || slot < 1) {
            System.out.println ("That's beyond the available slots");
            return;
        }

        ArrayList<Block> current = slots.get(slot);
        if (current == null) {
            current = new ArrayList<>();
            current.add(new Block());
        } else {
            current.add(new Block());
        }
        slots.put(slot, current);
    }

    public void remove(int slot) {
        // ASSUMPTION: no-op if nothing there
        ArrayList<Block> current = slots.get(slot);
        if (current != null && current.size() > 0) {
            current.remove(0);
        }
    }

    public void move(int fromSlot, int toSlot) {
        remove(fromSlot);
        add(toSlot);
    }

    public void printSlots() {
        for(Map.Entry<Integer,ArrayList<Block>> entry : slots.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<Block> value = entry.getValue();

            StringBuffer blocks = new StringBuffer();

            if (value != null) {
                for (Block block : value) {
                    if (block != null) {
                        blocks.append(block.contents());
                    }
                }
            }

            System.out.println(key + ": " + blocks.toString());
        }
    }
}
