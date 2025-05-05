package org.example.plant.protocol;

public interface Forwarding {
    Forwarding initForwardingTask(String name, String status, boolean resolved);

    String getName();

    String getStatus();

    boolean isResolved();
}
