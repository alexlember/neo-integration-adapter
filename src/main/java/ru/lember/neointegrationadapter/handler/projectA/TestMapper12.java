package ru.lember.neointegrationadapter.handler.projectA;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.Handler;
import ru.lember.neointegrationadapter.handler.Mapper;
import ru.lember.neointegrationadapter.message.Message;
import ru.lember.neointegrationadapter.message.SimpleMessage;

import javax.annotation.PostConstruct;

@Slf4j
public class TestMapper12 implements Mapper {

    private EmitterProcessor<Message> processor = EmitterProcessor.create();

    private final Handler next;

    public TestMapper12(Handler next) {
        this.next = next;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("TestMapper12 initialized");
        processor.subscribe(m -> {
            if (m.getEntity() instanceof String) {
                String modified = m.getEntity() + "_B12";
                next.processor().onNext(SimpleMessage.of(m.getId()).andEntity(modified));
            }
        }, e -> {
            log.error("TestMapper12 onError: " + e);
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
