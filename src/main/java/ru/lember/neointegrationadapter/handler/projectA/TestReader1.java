package ru.lember.neointegrationadapter.handler.projectA;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.Handler;
import ru.lember.neointegrationadapter.handler.Reader;
import ru.lember.neointegrationadapter.message.Message;
import ru.lember.neointegrationadapter.message.SimpleMessage;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestReader1 implements Reader {

    private ReplayProcessor<Message> processor = ReplayProcessor.create();

    private final Handler next;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public TestReader1(Handler next) {
        this.next = next;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("TestReader1 initialized");
        executorService.scheduleAtFixedRate(this::trigger, 2, 2, TimeUnit.SECONDS);
    }

    private void trigger() {
        log.info("TestReader1 trigger");
        Message message = SimpleMessage.of("1").andEntity("A1");
        next.processor().onNext(message);
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
