package org.boisdechet.adventofcode2019.computer;

import org.boisdechet.adventofcode2019.coord.PointL;
import org.boisdechet.adventofcode2019.opcode.OpCodeMachine;
import org.boisdechet.adventofcode2019.utils.InputUtil;
import org.boisdechet.adventofcode2019.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class NIC implements Runnable {
    private static int SLEEP_BOOTUP = 2000;
    private static int SLEEP_RUN = 100;

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
    private boolean hasData;

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

    @Override
    public void run() {
        int sendTo = -1;
        try {
            Thread.sleep(SLEEP_BOOTUP);
            while(!shutdown) {
                List<Long> inputs = new ArrayList<>();
                //if(Log.INFO) { Log.i(String.format("[%02d] %d messages in queue", address, msgToReceive.size())); }
                if(!msgToReceive.isEmpty()) {
                    PointL msg = msgToReceive.poll();
                    long[] newInputs = new long[] { msg.x, msg.y };
                    if(Log.INFO) { Log.i(String.format("[%02d] instructions: RECEIVE %s", address, InputUtil.convertToString(newInputs, ','))); }
                    sendTo = Math.toIntExact(machine.execute(newInputs));
                    hasData = true;
                }
                if(shutdown) { break; }
                if(!hasData) {
                    sendTo = sendTo >= 0 ? sendTo : Math.toIntExact(machine.execute(-1));
                    if(sendTo >= 0) {
                        long sendX = machine.execute(-1);
                        long sendY = machine.execute(-1);
                        SendInstruction instr = new SendInstruction(new PointL(sendX, sendY), sendTo);
                        if(Log.INFO) { Log.i(String.format("[%02d] instructions: %s", address, instr.toString())); }
                        network.send(sendTo, new PointL(sendX, sendY));
                        sendTo = -1;
                    } else {
                        hasData = false;
                    }
                }
                Thread.sleep(SLEEP_RUN);
            }
        } catch (InterruptedException e) {}
        if(Log.INFO) { Log.d(String.format("[%02d] interrupted", address)); }
        thread = null;
    }
}
