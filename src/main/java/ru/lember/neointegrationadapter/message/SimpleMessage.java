package ru.lember.neointegrationadapter.message;

import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class SimpleMessage implements Message {

    @Getter
    private @NonNull String id;

    @Getter
    private @NonNull DestinationInfo destinationInfo;

    @Getter
    private @NonNull MessageType type;

    @Getter
    private @Nullable Object entity;


    private SimpleMessage(String id, DestinationInfo destinationInfo, MessageType messageType, @Nullable Object entity) {
        this.id = id;
        this.destinationInfo = destinationInfo;
        this.type = messageType;
        this.entity = entity;
    }

    public static SimpleMessage of(@NonNull String id) {
        return new SimpleMessage(id, DestinationInfoImpl.createDefault(), MessageType.NONE, null);
    }

    public static SimpleMessage of(@NonNull String id, @NonNull DestinationType destinationType) {
        return new SimpleMessage(id, DestinationInfoImpl.of(destinationType), MessageType.NONE, null);
    }

    // watch consistensy

    public SimpleMessage andEntity(Object entity) {
        this.entity = entity;
        return this;
    }

    public SimpleMessage andType(MessageType type) {
        this.type = type;
        return this;
    }
}
