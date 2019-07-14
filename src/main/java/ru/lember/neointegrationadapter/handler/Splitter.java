package ru.lember.neointegrationadapter.handler;

import java.util.List;

public interface Splitter extends Handler {

    List<Handler> nextComponents();

}
