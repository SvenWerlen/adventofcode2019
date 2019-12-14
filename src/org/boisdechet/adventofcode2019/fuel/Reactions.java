package org.boisdechet.adventofcode2019.fuel;

import org.boisdechet.adventofcode2019.utils.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reactions {

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
    private int getFromStock(Map<String, Reaction.Chemical> stock, Reaction.Chemical c) {
        if(stock.containsKey(c.key)) {
            Reaction.Chemical chemInStock = stock.get(c.key);
            if(chemInStock.count > c.count) {
                chemInStock.count -= c.count;
                return 0;
            } else {
                int remaining = c.count - chemInStock.count;
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

    public int getRequiredOre(Reaction.Chemical chemical, Map<String, Reaction.Chemical> stock) {
        Log.d(String.format("Looking for %d %s", chemical.count, chemical.key));
        if(chemical.key.equals(Reactions.TYPE_ORE)) {
            return chemical.count;
        }
        Reaction reaction = reactions.get(chemical.key);
        if(reaction == null) {
            throw new IllegalStateException(String.format("Chemical reaction %s not found!", chemical.key));
        }
        // get from remaining if available
        int required = getFromStock(stock, new Reaction.Chemical(chemical.key, chemical.count));
        // compute how many times input is required
        int mult = (int)Math.ceil((float)required / reaction.getOutput().count);
        //Log.d(String.format("Chemical multiplier for %s is %d, considering %d is required", chemical.key, count, chemical.count));
        // recursion on each chemical required (input)
        int total = 0;
        for(Reaction.Chemical c : reaction.getInput()) {
            Reaction.Chemical reqC = new Reaction.Chemical(c.key, c.count * mult);
            Log.d(String.format("%d %s requires %d %s", chemical.count, chemical.key, reqC.count, reqC.key));
            total += getRequiredOre(reqC, stock);
        }
        // update remaining stock
        int remaining = mult * reaction.getOutput().count - required;
        if(remaining > 0) {
            addToStock(stock, new Reaction.Chemical(chemical.key, remaining));
            Log.d(String.format("Remaining %d %s added to stock!", remaining, chemical.key));
        }
        return total;
    }
}
