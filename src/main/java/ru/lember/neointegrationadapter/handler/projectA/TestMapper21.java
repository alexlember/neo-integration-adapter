package ru.lember.neointegrationadapter.handler.projectA;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.Handler;
import ru.lember.neointegrationadapter.handler.Mapper;
import ru.lember.neointegrationadapter.message.Message;
import ru.lember.neointegrationadapter.message.MessageType;
import ru.lember.neointegrationadapter.message.SimpleMessage;

import javax.annotation.PostConstruct;
import java.util.Random;

@Slf4j
public class TestMapper21 implements Mapper {

    private ReplayProcessor<Message> processor = ReplayProcessor.create();

    private final Handler next;

    public TestMapper21(Handler next) {
        this.next = next;
    }

    private MessageType[] types = {MessageType.ADD, MessageType.DEL, MessageType.NONE};

    @PostConstruct
    private void postConstruct() {
        log.info("TestMapper21 initialized");
        processor.subscribe(m -> {
            if (m.getEntity() instanceof String) {
                String modified = m.getEntity() + "_B21";

                Random dice = new Random();
                int n = dice.nextInt(types.length);

                next.processor().onNext(SimpleMessage.of(m.getId()).andType(types[n]).andEntity(modified));
            }
        }, e -> {
            log.error("TestMapper21 onError: " + e);
        });
    }


    @Override
    public FluxProcessor<Message, Message> processor() {
        return processor;
    }

    @Override
    public Handler next() {
        return next;
    }
}
