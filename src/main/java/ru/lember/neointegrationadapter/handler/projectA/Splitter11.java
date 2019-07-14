package ru.lember.neointegrationadapter.handler.projectA;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.Handler;
import ru.lember.neointegrationadapter.handler.Splitter;
import ru.lember.neointegrationadapter.message.Message;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Splitter11 implements Splitter {

    private ReplayProcessor<Message> processor = ReplayProcessor.create();

    private final List<Handler> nextComponents;

    public Splitter11(Handler... nextComponents) {
        this.nextComponents = Arrays.stream(nextComponents).collect(Collectors.toList());
    }

    @PostConstruct
    private void postConstruct() {
        log.info("Splitter11 initialized");
        processor.subscribe(m -> {
            nextComponents.forEach(
                    next -> next.processor().onNext(m));
        }, e -> {
            log.error("Splitter11 onError: " + e);
        });
    }


    @Override
    public List<Handler> nextComponents() {
        return nextComponents;
    }

    @Override
    public FluxProcessor<Message, Message> processor() {
        return processor;
    }
}
