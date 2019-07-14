package ru.lember.neointegrationadapter.handler.projectA;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.Writer;
import ru.lember.neointegrationadapter.message.Message;

import javax.annotation.PostConstruct;

@Slf4j
@NoArgsConstructor
public class TestWriter2 implements Writer {

    private EmitterProcessor<Message> processor = EmitterProcessor.create();

    @PostConstruct
    private void postConstruct() {
        log.info("TestWriter2 initialized");
        processor.subscribe(m -> {
            if (m.getEntity() instanceof String) {
                String modified = m.getEntity() + "_C2";
                log.info("TestWriter2 final message: " + modified);
            }
        }, e -> {
            log.error("TestWriter2 onError: " + e);
        });
    }

    @Override
    public FluxProcessor<Message, Message> processor() {
        return processor;
    }

}
