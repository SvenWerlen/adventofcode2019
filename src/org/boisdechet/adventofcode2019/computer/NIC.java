package org.boisdechet.adventofcode2019.computer;

import org.boisdechet.adventofcode2019.coord.PointL;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class NIC implements Runnable {
    private static int SLEEP_BOOTUP = 0;
    private static int SLEEP_RUN = 5;

    public static class SendInstruction {
        public PointL data;
        public int address;
        public SendInstruction(PointL data, int address) { this.data = data; this.address = address; }

        @Override
        public String toString() {
            return String.format("SEND %s TO %d", data.toString(), address);
        }
    }

    private int address;
    private OpCodeMachine machine;
    private Thread thread;
    private volatile boolean shutdown = false;
    private LinkedBlockingDeque<PointL> msgToReceive;
    private Network network;
    private int idle;

    public NIC(long[] instr, int address, Network network) {
        this.address = address;
        this.network = network;
        machine = new OpCodeMachine(instr, address);
        machine.setDefaultParam(-1);
        machine.setReturnOnInput();
        msgToReceive = new LinkedBlockingDeque<>();
    }

    public synchronized void boot() {
        if(thread == null) {
            if(Log.INFO) { Log.d(String.format("[%02d] Booting ...", address)); }
            int code = Math.toIntExact(machine.execute(this.address));
            if(Log.INFO) { Log.d(String.format("[%02d] Address requested with response %d", address, code)); }
            thread = new Thread(this);
            thread.start();
        }
    }

    public boolean isIdle() {
        return this.idle > 3;
    }

    public void receive(long x, long y) {
        if(Log.INFO) { Log.d(String.format("[%02d] Message received: %s", address, new PointL(x,y))); }
        msgToReceive.add(new PointL(x, y));
    }

    public void shutdown() {
        if(Log.INFO) { Log.d("Shutting down!"); }
        shutdown = true;
        if(thread != null) {
            thread.interrupt();
        }
        if(thread != null) {
            thread.stop();
        }
    }

    private void checkCondition(boolean cond, String errorMessage) {
        if(!cond) {
            throw new IllegalStateException(errorMessage);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(SLEEP_BOOTUP);
            while(!shutdown) {
                int sendTo = -1;
                // RECEIVE
                if(isIdle() && !msgToReceive.isEmpty()) {
                    idle = 0;
                    PointL msg = msgToReceive.poll();
                    if(Log.INFO) { Log.i(String.format("[%02d] instructions: RECEIVE (%d,%d)", address, msg.x, msg.y)); }
                    sendTo = Math.toIntExact(machine.execute(new long[] { msg.x, msg.y }));
                    checkCondition(sendTo >= 0 || sendTo == OpCodeMachine.WAIT_FOR_INPUT, String.format("No output expected after RECEIVE instructions! (%d)", sendTo));
                    if(Log.INFO) { Log.i(String.format("[%02d] result is %d", address, sendTo)); }
                }
                sendTo = sendTo >= 0 ? sendTo : Math.toIntExact(machine.execute(-1));
                checkCondition(sendTo != OpCodeMachine.HALT, "OpCode machine ended!");
                // idle?
                if(sendTo == OpCodeMachine.WAIT_FOR_INPUT) {
                    idle = Math.max(idle+1, Integer.MAX_VALUE);
                    //Log.i(String.format("[%02d] Idle", address));
                    Thread.sleep(SLEEP_RUN);
                    continue;
                } else if(sendTo >= 0) {
                    idle = 0;
                    long sendX = machine.execute(-1);
                    checkCondition(sendX != OpCodeMachine.HALT && sendX != OpCodeMachine.WAIT_FOR_INPUT,
                            String.format("[%02d] Coordinate X was expected after sender [%02d] but '%s' received", address, sendTo, sendX == OpCodeMachine.WAIT_FOR_INPUT ? "wait input" : "halt"));
                    long sendY = machine.execute(-1);
                    checkCondition(sendY != OpCodeMachine.HALT && sendY != OpCodeMachine.WAIT_FOR_INPUT,
                            String.format("[%02d] Coordinate Y was expected but '%s' received", address, sendY == OpCodeMachine.WAIT_FOR_INPUT ? "wait input" : "halt"));
                    SendInstruction instr = new SendInstruction(new PointL(sendX, sendY), sendTo);
                    if(Log.INFO) { Log.i(String.format("[%02d] instructions: %s", address, instr.toString())); }
                    network.send(sendTo, new PointL(sendX, sendY));
                }
            }
        } catch (InterruptedException e) {}
        if(Log.INFO) { Log.d(String.format("[%02d] interrupted", address)); }
        thread = null;
    }
}
