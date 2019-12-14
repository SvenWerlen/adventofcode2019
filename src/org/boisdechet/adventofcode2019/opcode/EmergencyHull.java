package org.boisdechet.adventofcode2019.opcode;

import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.HashMap;
import java.util.Map;

public class EmergencyHull {

    private Map<Point, Integer> panels;

    public EmergencyHull() {
        panels = new HashMap<>();
    }

    public void paint(Point point, int color) {
        panels.put(point, color);
    }

    public int paintedPanelCount() {
        return panels.size();
    }

    public int getColor(Point position) {
        if(panels.containsKey(position)) {
            return panels.get(position);
        } else {
            return Robot.Output.COLOR_BLACK;
        }
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("Hull:\n");
        int minX = 0;
        int maxX = 0;
        int minY = 0;
        int maxY = 0;
        for(Point p : panels.keySet()) {
            if(p.x < minX) { minX = p.x; }
            if(p.y < minY) { minY = p.y; }
            if(p.x > maxX) { maxX = p.x; }
            if(p.y > maxY) { maxY = p.y; }
        }
        for(int y = minY-1; y <= maxY+1; y++) {
            for(int x = minX-1; x <= maxX+1; x++) {
                Point p = new Point(x,y);
                if(panels.containsKey(p)) {
                    buf.append(panels.get(p) == Robot.Output.COLOR_BLACK ? '.' : '#');
                } else {
                    buf.append(' ');
                }
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}
