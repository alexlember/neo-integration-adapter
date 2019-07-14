package ru.lember.neointegrationadapter.message;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.lang.Nullable;

public class DestinationInfoImpl implements DestinationInfo {

    @Getter
    private DestinationType destinationType;

    @Getter
    private String value;

    private DestinationInfoImpl(@NonNull DestinationType destinationType, @Nullable String value) {
        this.destinationType = destinationType;
        this.value = value;
    }

    public static DestinationInfoImpl of(DestinationType destinationType) {
        if (destinationType == null) {
            return new DestinationInfoImpl(DestinationType.NONE, null);
        }

        return new DestinationInfoImpl(destinationType, null);
    }

    public static DestinationInfoImpl createDefault() {
        return of(DestinationType.NONE);
    }

    public DestinationInfoImpl andValue(String value) {
        this.value = value;
        return this;
    }

}
