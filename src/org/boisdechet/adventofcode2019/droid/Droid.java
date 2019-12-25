package org.boisdechet.adventofcode2019.droid;

import org.boisdechet.adventofcode2019.opcode.OpCode;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;
import org.boisdechet.adventofcode2019.utils.Permutations;
import org.boisdechet.adventofcode2019.utils.PermutationsSubset;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Droid {

    public static final String COMMAND_MOVE_NORTH = "north";
    public static final String COMMAND_MOVE_SOUTH = "south";
    public static final String COMMAND_MOVE_EAST  = "east";
    public static final String COMMAND_MOVE_WEST  = "west";
    public static final String COMMAND_TAKE       = "take ([a-z ]+)";
    public static final String COMMAND_DROP       = "drop ([a-z ]+)";
    public static final String COMMAND_INVENTORY  = "inv";
    public static final String OUTPUT_ROOM        = ".*== ([a-zA-Z ]+) ==.*";

    public static final String[] ITEMS = new String[] { "mutex", "dark matter", "klein bottle", "tambourine", "fuel cell", "astrolabe", "monolith", "cake" };
    public static final String[] ITEMS_OPTIMIZED = new String[] { "dark matter", "tambourine", "astrolabe", "monolith" };

    private static final String[] COMMANDS_TAKE_ALL = new String[]{
            "south", "take cake",
            "south", "west", "take mutex",
            "east", "north", "north", "west", "take klein bottle",
            "south", "east", "take monolith",
            "south", "take fuel cell",
            "west", "west", "take astrolabe",
            "east", "east", "north", "west", "north", "west", "north", "take tambourine",
            "south", "west", "take dark matter", "west",
            "drop mutex", "drop dark matter", "drop monolith", "drop klein bottle", "drop tambourine", "drop fuel cell", "drop astrolabe", "drop cake"
    };

    private static final String[] COMMANDS_TAKE_ALL_OPTIMIZED = new String[]{
            "west", "south", "east", "take monolith",
            "south", "west", "west", "take astrolabe",
            "east", "east", "north", "west", "north", "west", "north", "take tambourine",
            "south", "west", "take dark matter", "west",
            "drop dark matter", "drop monolith", "drop tambourine", "drop astrolabe"
    };

    private OpCodeMachine machine;
    private Pattern pTake;
    private Pattern pDrop;
    private Pattern oRoom;
    private Set<String> validCommands;

    public Droid(long[] instr) {
        this.machine = new OpCodeMachine(instr);
        machine.setReturnOnInput();
        this.pTake = Pattern.compile(COMMAND_TAKE);
        this.pDrop = Pattern.compile(COMMAND_DROP);
        this.oRoom = Pattern.compile(OUTPUT_ROOM);
        this.validCommands = new HashSet<>();
        validCommands.add(COMMAND_MOVE_NORTH);
        validCommands.add(COMMAND_MOVE_SOUTH);
        validCommands.add(COMMAND_MOVE_EAST);
        validCommands.add(COMMAND_MOVE_WEST);
        validCommands.add(COMMAND_INVENTORY);

    }

    /**
     * Prompts for input and returns command
     */
    private String promptForInput() {
        while(true) {
            Log.i("Command (ENTER to exit): ", false);
            Scanner scan = new Scanner(System.in);
            String command = scan.nextLine();
            if (command.length() == 0) {
                return null;
            }
            if (validCommands.contains(command)) {
                return command;
            }
            Matcher m = pTake.matcher(command);
            if (m.matches()) {
                return command;
            }
            m = pDrop.matcher(command);
            if (m.matches()) {
                return command;
            }
            Log.i("Invalid command! Usage: [north|south|east|west|take <obj>|drop <obj>|inv|exit");
        }
    }

    /**
     * Try combination of item to match required weight
     * @return password or -1 if no combination work
     */
    private long checkCombination(List<String> items) {
        String[] comm = new String[items.size()*2+1];
        comm[items.size()] = "north";
        String asString = "";
        for(int i=0; i<items.size(); i++) {
            String item = items.get(i);
            comm[i] = "take " + item;
            comm[comm.length-1-i] = "drop " +item;
            asString += item + " ";
        }
        StringBuffer out2 = new StringBuffer();
        int idx = 0;
        int[] inputs = null;
        long code;
        if(Log.DEBUG) { Log.d(String.format("Trying combination: %s", asString)); }
        while(idx < comm.length) {
            inputs = InputUtil.convertStringToIntArray(comm[idx] + "\n");
            while ((code = machine.execute(inputs)) != OpCodeMachine.WAIT_FOR_INPUT) {
                if(code == OpCodeMachine.HALT) { idx = comm.length; break; }
                inputs = null;
                out2.append((char) code);
            }
            idx++;
        }
        String output = out2.toString().replaceAll("\n", "");
        if(Pattern.compile(".*You don't see that item here..*").matcher(output).matches()) {
            throw new IllegalStateException("Trying to take item that is not available!");
        } else if(Pattern.compile(".*You don't have that item..*").matcher(output).matches()) {
            throw new IllegalStateException("Trying to drop item that is not in inventory!");
        }
        if(Pattern.compile(".*Droids on this ship are heavier than the detected value!.*").matcher(output).matches()) {
            return -1;
        } else if(Pattern.compile(".*Droids on this ship are lighter than the detected value!.*").matcher(output).matches()) {
            return -1;
        } else if(Pattern.compile(".*Oh, hello! You should be able to get in by typing (\\d+) on the keypad at the main airlock..*").matcher(output).matches()) {
            Matcher m = Pattern.compile(".*Oh, hello! You should be able to get in by typing (\\d+) on the keypad at the main airlock..*").matcher(output);
            if(m.matches()) {
                return Long.parseLong(m.group(1));
            } else {
                return 0;
            }
        } else {
            throw new IllegalStateException("Invalid state!");
        }
    }

    /**
     * Collects all the items (or only the required ones if optimized is true)
     * Try all combinations of items in order to pass the security
     */
    public long findCombination(boolean optimized) {
        String[] commands = (optimized ? COMMANDS_TAKE_ALL_OPTIMIZED : COMMANDS_TAKE_ALL);
        int[] inputs = null;
        int inputIdx = 0;
        while(true) {
            long code;
            StringBuffer output = new StringBuffer();
            while((code = machine.execute(inputs)) != OpCodeMachine.WAIT_FOR_INPUT) {
                inputs = null;
                output.append((char)code);
            }
            //Log.i(output.toString());
            String command;
            if(inputIdx < commands.length) {
                command = commands[inputIdx];
            } else {
                command = null;
                int minObjects = optimized ? 4 : 1;
                String[] items = optimized ? ITEMS_OPTIMIZED : ITEMS;
                for(int i=minObjects; i<=items.length; i++) {
                    List<List<String>> list = PermutationsSubset.choose(Arrays.asList(items), i, true);
                    // test with all combinations
                    for (List<String> comb : list) {
                        long password = checkCombination(comb);
                        if (password > 0) {
                            return password;
                        }
                    }
                }
            }
            if(command == null) { throw new IllegalStateException("No command!"); }
            inputIdx++;
            inputs = InputUtil.convertStringToIntArray(command + "\n");
        }
    }

    /**
     * User-interactive mode
     */
    public long runUserInteractive() {
        int[] inputs = null;
        while(true) {
            long code;
            StringBuffer output = new StringBuffer();
            while((code = machine.execute(inputs)) != OpCodeMachine.WAIT_FOR_INPUT) {
                inputs = null;
                output.append((char)code);
            }
            Log.i(output.toString());
            String command = promptForInput();
            if(command == null) {
                Log.i("Exiting... bye bye!");
                return -1;
            }
            inputs = InputUtil.convertStringToIntArray(command + "\n");
        }
    }
}
