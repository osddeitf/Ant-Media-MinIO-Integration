package com.amazonaws.event;

public class ProgressEvent {

    private final ProgressEventType type;

    public ProgressEvent(ProgressEventType type) {
        this.type = type;
    }

    public ProgressEventType getEventType() {
        return type;
    }
}
