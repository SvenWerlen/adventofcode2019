package org.boisdechet.adventofcode2019.fuel;

import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reactions {

    public static final float RATIO = 0.75f;
    public static final int MAGNITUDE = 10;

    public static final String TYPE_ORE = "ORE";
    public static final String TYPE_FUEL = "FUEL";

    private Map<String, Reaction> reactions;

    public Reactions(List<Reaction> reactions) {
        this.reactions = new HashMap<>();
        for(Reaction r : reactions) {
            Reaction.Chemical output = r.getOutput();
            if(this.reactions.containsKey(output.key)) {
                throw new IllegalStateException(String.format("Illegal reaction duplicate %s!", output.key));
            }
            this.reactions.put(output.key, r);
        }
    }

    /**
     * Looks for remaining chemical and decreases stock if any
     * @param stock available stock
     * @param c required chemical
     * @return remaining required chemical
     */
    private long getFromStock(Map<String, Reaction.Chemical> stock, Reaction.Chemical c) {
        if(stock.containsKey(c.key)) {
            Reaction.Chemical chemInStock = stock.get(c.key);
            if(chemInStock.count > c.count) {
                chemInStock.count -= c.count;
                return 0;
            } else {
                long remaining = c.count - chemInStock.count;
                stock.remove(c.key);
                return remaining;
            }
        } else {
            return c.count;
        }
    }

    /**
     * Add chemical to stock
     * @param stock available stock
     * @param c remaining chemical
     */
    private void addToStock(Map<String, Reaction.Chemical> stock, Reaction.Chemical c) {
        if(stock.containsKey(c.key)) {
            stock.get(c.key).count += c.count;
        } else {
            stock.put(c.key, c);
        }
    }

    public long getRequiredOre(Reaction.Chemical chemical, Map<String, Reaction.Chemical> stock) {
        if(Log.DEBUG) { Log.d(String.format("Looking for %d %s", chemical.count, chemical.key)); }
        if(chemical.key.equals(Reactions.TYPE_ORE)) {
            return chemical.count;
        }
        Reaction reaction = reactions.get(chemical.key);
        if(reaction == null) {
            throw new IllegalStateException(String.format("Chemical reaction %s not found!", chemical.key));
        }
        // get from remaining if available
        long required = getFromStock(stock, new Reaction.Chemical(chemical.key, chemical.count));
        if(required == 0) {
            return 0;
        }
        // compute how many times input is required
        long mult = (long)Math.ceil((double)required / reaction.getOutput().count);
        if(Log.DEBUG) { Log.d(String.format("Chemical multiplier for %s is %d, considering %d is required and reaction provides %d", chemical.key, mult, required, reaction.getOutput().count)); }
        // recursion on each chemical required (input)
        long total = 0;
        for(Reaction.Chemical c : reaction.getInput()) {
            Reaction.Chemical reqC = new Reaction.Chemical(c.key, c.count * mult);
            if(Log.DEBUG) { Log.d(String.format("%d %s requires %d %s", chemical.count, chemical.key, reqC.count, reqC.key));}
            total += getRequiredOre(reqC, stock);
        }
        // update remaining stock
        long remaining = mult * reaction.getOutput().count - required;
        if(remaining > 0) {
            addToStock(stock, new Reaction.Chemical(chemical.key, Math.toIntExact(remaining)));
            if(Log.DEBUG) { Log.d(String.format("Remaining %d %s added to stock!", remaining, chemical.key));}
        } else if(remaining < 0) {
            throw new IllegalStateException(String.format("Invalid remaining value (%d)", remaining));
        }
        return total;
    }

    public static String getStockAsString(Map<String, Reaction.Chemical> stock) {
        StringBuffer buf = new StringBuffer();
        for(Reaction.Chemical c : stock.values()) {
            buf.append(c).append(", ");
        }
        buf.delete(buf.length()-2, buf.length());
        return buf.toString();
    }

    public long getMaxFuelForOre(long amount, long min, long max) {
        if(Math.abs(min-max)==1) {
            return min;
        }
        long current = (long)Math.ceil(((double)min+max)/2);
        long oreForFuel = getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, current), new HashMap<String, Reaction.Chemical>());
        if(Log.DEBUG) { Log.d(String.format("%d ORE required for %d FUEL (%d,%d,%d)", oreForFuel, current, min, current, max));}
        if(oreForFuel < 0) {
            throw new IllegalStateException(String.format("Required ORE cannot be negative! (%d)", current));
        }
        if(oreForFuel <= amount) {
            return getMaxFuelForOre(amount, current, max-1);
        } else {
            return getMaxFuelForOre(amount, min, current);
        }
    }

    public long getMaxFuelForOre(long amount) {
        long oreForFuel = getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, 1), new HashMap<String, Reaction.Chemical>());
        long max = amount / oreForFuel;
        long fuel = getMaxFuelForOre(amount, 0, 2*max);
        Map<String, Reaction.Chemical> stock = new HashMap<>();
        oreForFuel = getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, fuel), stock);
        if(Log.DEBUG) { Log.d(String.format("Total ORE required: %d / %d (FUEL: %d)", oreForFuel, amount, fuel));}
        // compute the rest
        long remaining = amount - oreForFuel;
        while(true) {
            long required = getRequiredOre(new Reaction.Chemical(Reactions.TYPE_FUEL, 1), stock);
            if(Log.DEBUG) { Log.d(String.format("ORE required: %d / %d", required, remaining));}
            if(required > remaining) {
                return fuel;
            }
            remaining-=required;
            fuel++;
        }

    }
}
