package org.boisdechet.adventofcode2019.opcode;

import org.boisdechet.adventofcode2019.coord.Point;

import java.util.ArrayList;
import java.util.List;

public class ArcadeCabinet {

    private OpCodeMachine prog;

    public ArcadeCabinet(OpCodeMachine program) {
        this.prog = program;
    }

    public static class Output {
        public final static int TILE_EMPTY   = 0;
        public final static int TILE_WALL    = 1;
        public final static int TILE_BLOCK   = 2;
        public final static int TILE_HPADDLE = 3;
        public final static int TILE_BALL    = 4;
        public Point coord;
        public int tileType;

        public Output() {}

        public boolean isValid() {
            return coord != null && (tileType >= TILE_EMPTY && tileType <= TILE_BALL);
        }

        public static String getTypeAsString(int type) {
            switch(type) {
                case TILE_EMPTY: return "empty";
                case TILE_WALL: return "wall";
                case TILE_BLOCK: return "block";
                case TILE_HPADDLE: return "h.paddle";
                case TILE_BALL: return "ball";
                default: throw new IllegalStateException(String.format("Invalid type %d!", type));
            }
        }

        @Override
        public String toString() {
            if(isValid()) {
                return String.format("Output (%s,%s)", coord, getTypeAsString(tileType));
            } else {
                return String.format("Output (%s,%d)", coord, tileType);
            }
        }
    }

    public Output next() {
        ArcadeCabinet.Output output = new ArcadeCabinet.Output();
        long result1 = prog.execute(0, 0, false);
        if(result1 == OpCodeMachine.HALT) {
            return null;
        }
        long result2 = prog.execute(0, 0, false);
        if(result2 == OpCodeMachine.HALT) {
            return null;
        }
        long result3 = prog.execute(0, 0, false);
        if(result3 == OpCodeMachine.HALT) {
            return null;
        }
        output.coord = new Point(Math.toIntExact(result1), Math.toIntExact(result2));
        output.tileType = Math.toIntExact(result3);
        return output;
    }

    public List<Output> getOutputsTillExit() {
        List<Output> list = new ArrayList<>();
        Output out;
        while((out = next()) != null) {
            list.add(out);
        }
        return list;
    }
}
