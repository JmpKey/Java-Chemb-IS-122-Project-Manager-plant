package org.example.plant.realization;

import org.example.plant.protocol.Forwarding;

public class ForwardingTask implements Forwarding {
    private String name;
    private String status;
    private boolean resolved;

    @Override
    public Forwarding initForwardingTask(String name, String status, boolean resolved) {
        this.name = name;
        this.status = status;
        this.resolved = resolved;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean isResolved() {
        return resolved;
    }
}
