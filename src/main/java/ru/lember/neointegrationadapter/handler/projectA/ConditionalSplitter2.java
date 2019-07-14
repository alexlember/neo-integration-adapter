package ru.lember.neointegrationadapter.handler.projectA;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.ConditionalTriSplitter;
import ru.lember.neointegrationadapter.handler.Handler;
import ru.lember.neointegrationadapter.message.Message;

import javax.annotation.PostConstruct;
import java.util.function.BiFunction;

@Slf4j
public class ConditionalSplitter2 implements ConditionalTriSplitter {

    private EmitterProcessor<Message> processor = EmitterProcessor.create();

    private final Handler first;
    private final Handler second;
    private final Handler third;

    private final BiFunction<Message, ConditionalTriSplitter, Handler> switchFunction;

    public ConditionalSplitter2(Handler first, Handler second, Handler third, BiFunction<Message, ConditionalTriSplitter, Handler> switchFunction) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.switchFunction = switchFunction;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("ConditionalSplitter2 initialized");
        processor.subscribe(m -> {
            switchFunction.apply(m, this).processor().onNext(m);
        }, e -> {
            log.error("Splitter11 onError: " + e);
        });
    }


    @Override
    public Handler first() {
        return first;
    }

    @Override
    public Handler second() {
        return second;
    }

    @Override
    public Handler third() {
        return third;
    }

    @Override
    public FluxProcessor<Message, Message> processor() {
        return processor;
    }
}
