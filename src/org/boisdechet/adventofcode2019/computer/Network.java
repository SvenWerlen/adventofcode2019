package org.boisdechet.adventofcode2019.computer;

import org.boisdechet.adventofcode2019.coord.Point;
import org.boisdechet.adventofcode2019.coord.PointL;

import java.util.HashMap;
import java.util.Map;

public class Network {

    private PointL dataReceived;
    private int receiverId;
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
                Thread.sleep(500);
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
        }
        else if(computers.containsKey(receiverId)) {
            computers.get(receiverId).receive(p.x, p.y);
        } else {
            throw new IllegalStateException("Cannot send to missing computer " + receiverId);
        }
    }
}
