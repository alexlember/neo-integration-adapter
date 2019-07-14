package ru.lember.neointegrationadapter.handler;

import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.message.Message;

public interface Handler {

    FluxProcessor<Message, Message> processor();

}
