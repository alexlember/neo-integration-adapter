package ru.lember.neointegrationadapter.message;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface DestinationInfo {

    @NonNull
    DestinationType getDestinationType();

    @Nullable
    String getValue();

}
