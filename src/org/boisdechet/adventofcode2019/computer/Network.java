package org.boisdechet.adventofcode2019.computer;

import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.coord.PointL;

import java.util.HashMap;
import java.util.Map;

public class Network {

    private static int SLEEP_CHECK = 50;
    public static final int DEVICE_NAT = 255;

    private PointL dataReceived, lastNATPacket;
    private int receiverId;
    private boolean resetOnIde;

    private Map<Integer, NIC> computers;

    public Network(long[] instr, int computers) {
        this.dataReceived = null;
        this.computers = new HashMap<>();
        for(int i=0; i<computers; i++) {
            this.computers.put(i, new NIC(instr, i, this));
        }
    }

    public PointL runUntilPacketSentTo(int receiverId) {
        this.receiverId = receiverId;
        for(NIC n : computers.values()) {
            n.boot();
        }
        while(dataReceived == null) {
            try {
                Thread.sleep(SLEEP_CHECK);
            } catch (InterruptedException e) {}
        }
        for(NIC n : computers.values()) {
            n.shutdown();
        }
        return dataReceived;
    }

    public PointL runUntilPacketSentTo0TwiceInARow() {
        long yPrev = -1;
        this.receiverId = DEVICE_NAT;
        for(NIC n : computers.values()) {
            n.boot();
        }
        while(true) {
            try {
                boolean idle = true;
                for(NIC n : computers.values()) {
                    if(!n.isIdle()) {
                        idle = false;
                        break;
                    }
                }
                if(idle) {
                    if(yPrev == lastNATPacket.y) {
                        break;
                    }
                    send(0, lastNATPacket);
                    yPrev = lastNATPacket.y;
                }
                Thread.sleep(SLEEP_CHECK);
            } catch (InterruptedException e) {}
        }
        for(NIC n : computers.values()) {
            n.shutdown();
        }
        return dataReceived;
    }

    public synchronized void send(int receiverId, PointL p) {
        if(this.receiverId == receiverId) {
            dataReceived = p;
            if(receiverId == DEVICE_NAT) {
                lastNATPacket = p;
            }
        }
        else if(computers.containsKey(receiverId)) {
            computers.get(receiverId).receive(p.x, p.y);
        } else {
            throw new IllegalStateException("Cannot send to missing computer " + receiverId);
        }
    }
}
