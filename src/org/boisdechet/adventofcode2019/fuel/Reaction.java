package org.boisdechet.adventofcode2019.fuel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reaction {

    private List<Chemical> input;
    private Chemical output;

    public static class Chemical {
        public String key;
        public int count;
        public Chemical(String key, int count) {
            this.key = key;
            this.count = count;
        }

        @Override
        public String toString() {
            return String.format("%d %s", count, key);
        }
    }

    public Reaction(String init) {
        Pattern p = Pattern.compile("(.+) => (\\d+) ([A-Z]+)");
        Matcher m = p.matcher(init);
        if (!m.matches()) {
            throw new IllegalStateException(String.format("Illegal reaction format: %s", init));
        }
        output = new Chemical(m.group(3), Integer.parseInt(m.group(2)));
        input = new ArrayList<>();
        for (String in : m.group(1).split(", ")) {
            p = Pattern.compile("(\\d+) ([A-Z]+)");
            m = p.matcher(in);
            if (!m.matches()) {
                throw new IllegalStateException(String.format("Illegal input chemical format: %s", init));
            }
            input.add(new Chemical(m.group(2), Integer.parseInt(m.group(1))));
        }
    }

    public Chemical getOutput() {
        return output;
    }

    public List<Chemical> getInput() {
        return input;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for(Chemical c : input) {
            buf.append(c).append(", ");
        }
        buf.delete(buf.length()-2, buf.length());
        buf.append(" => ");
        buf.append(output);
        return buf.toString();
    }
}
