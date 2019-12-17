package org.boisdechet.adventofcode2019.opcode;

import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            return coord != null && coord.x >= 0 && coord.y >= 0 && (tileType >= TILE_EMPTY && tileType <= TILE_BALL);
        }

        public boolean isScore() {
            return coord != null && coord.x == -1 && coord.y == 0;
        }

        public int getScore() {
            if(isScore()) {
                return tileType;
            } else {
                throw new IllegalStateException(String.format("Invalid request for score! (%s,%d)", coord, tileType));
            }
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

    public Output next(int input) {
        ArcadeCabinet.Output output = new ArcadeCabinet.Output();
        long result1 = prog.execute(input);
        if(result1 == OpCodeMachine.HALT) {
            return null;
        }
        long result2 = prog.execute(input);
        if(result2 == OpCodeMachine.HALT) {
            return null;
        }
        long result3 = prog.execute(input);
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
        while((out = next(0)) != null) {
            list.add(out);
        }
        return list;
    }

    public long playTillExit() {
        Output out;
        int loop = 0;
        int input = 0;
        int ballX = 0;
        int paddX = 0;
        long curScore = 0;
        while((out = next(input)) != null) {
            if(out.isScore()) {
                curScore = out.getScore();
            }
            else if(out.tileType == Output.TILE_BALL) {
                Log.d(String.format("Ball at position %s (%d)", out.coord, loop));
                ballX = out.coord.x;
            }
            else if(out.tileType == Output.TILE_HPADDLE) {
                Log.d(String.format("Paddle at position %s (%d)", out.coord, loop));
                paddX = out.coord.x;
            }
            loop++;
            input = ballX > paddX ? 1 : ballX < paddX ? -1 : 0;
        }
        return curScore;
    }

    public static String getDisplay(List<Output> outputs) {
        int maxX = 0;
        int maxY = 0;
        Map<Point, Integer> map = new HashMap<>();
        for(Output out : outputs) {
            if(!out.isValid()) {
                if(out.isScore()) {
                    Log.d(String.format("Current score is: %d", out.getScore()));
                } else {
                    throw new IllegalStateException(String.format("Invalid output: %s", out.toString()));
                }
            }
            if(out.coord.x > maxX) { maxX = out.coord.x; }
            if(out.coord.y > maxY) { maxY = out.coord.y; }
            map.put(out.coord, out.tileType);
        }
        StringBuffer buf = new StringBuffer();
        for(int y=0; y<=maxY; y++) {
            for(int x=0; x<=maxX; x++) {
                Point pos = new Point(x,y);
                if(map.containsKey(pos)) {
                    switch(map.get(pos)) {
                        case Output.TILE_EMPTY: buf.append(' '); break;
                        case Output.TILE_WALL: buf.append('|'); break;
                        case Output.TILE_BLOCK: buf.append('#'); break;
                        case Output.TILE_HPADDLE: buf.append('='); break;
                        case Output.TILE_BALL: buf.append('o'); break;
                    }
                } else {
                    buf.append(' ');
                }
            }
            buf.append('\n');
        }
        return buf.toString();
    }
}
