package ru.lember.neointegrationadapter.message;

import org.springframework.lang.NonNull;

public interface Message {

    @NonNull
    String getId();

    @NonNull
    DestinationInfo getDestinationInfo();

    MessageType getType();

    Object getEntity();

}
