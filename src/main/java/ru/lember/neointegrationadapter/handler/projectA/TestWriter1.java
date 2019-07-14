package ru.lember.neointegrationadapter.handler.projectA;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ReplayProcessor;
import reactor.core.publisher.FluxProcessor;
import ru.lember.neointegrationadapter.handler.Writer;
import ru.lember.neointegrationadapter.message.Message;

import javax.annotation.PostConstruct;

@Slf4j
@NoArgsConstructor
public class TestWriter1 implements Writer {

    private ReplayProcessor<Message> processor = ReplayProcessor.create();

    @PostConstruct
    private void postConstruct() {
        log.info("TestWriter1 initialized");
        processor.subscribe(m -> {
            if (m.getEntity() instanceof String) {
                String modified = m.getEntity() + "_C1";
                log.info("TestWriter1 final message: " + modified);
            }
        }, e -> {
            log.error("TestWriter1 onError: " + e);
        });
    }

    @Override
    public FluxProcessor<Message, Message> processor() {
        return processor;
    }

}
