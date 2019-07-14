package ru.lember.neointegrationadapter.handler.projectA;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.Handler;
import ru.lember.neointegrationadapter.handler.Mapper;
import ru.lember.neointegrationadapter.message.Message;
import ru.lember.neointegrationadapter.message.SimpleMessage;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
public class TestMapper11 implements Mapper {

    private ReplayProcessor<Message> processor = ReplayProcessor.create();

    private final Handler next;

    private Disposable subscribtion;

    public TestMapper11(Handler next) {
        this.next = next;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("TestMapper11 initialized");
        subscribtion = processor.subscribe(m -> {
            if (m.getEntity() instanceof String) {
                String modified = m.getEntity() + "_B11";
                next.processor().onNext(SimpleMessage.of(m.getId()).andEntity(modified));
            }
        }, e -> {
            log.error("TestMapper11 onError: " + e);
        });
        String s = "";
    }

    @PreDestroy
    private void preDestroy() {
        subscribtion.dispose();
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
