package ru.lember.neointegrationadapter.handler.projectA;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.Handler;
import ru.lember.neointegrationadapter.handler.Mapper;
import ru.lember.neointegrationadapter.message.Message;
import ru.lember.neointegrationadapter.message.SimpleMessage;

import javax.annotation.PostConstruct;

@Slf4j
public class TestMapper13 implements Mapper {

    private ReplayProcessor<Message> processor = ReplayProcessor.create();

    private final Handler next;

    public TestMapper13(Handler next) {
        this.next = next;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("TestMapper13 initialized");
        processor.subscribe(m -> {
            if (m.getEntity() instanceof String) {
                String modified = m.getEntity() + "_B13!";
                next.processor().onNext(SimpleMessage.of(m.getId()).andEntity(modified));
            }
        }, e -> {
            log.error("TestMapper13 onError: " + e);
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
