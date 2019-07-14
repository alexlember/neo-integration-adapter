package ru.lember.neointegrationadapter.handler;

public interface ConditionalBiSplitter extends Handler {

    Handler first();
    Handler second();

}
