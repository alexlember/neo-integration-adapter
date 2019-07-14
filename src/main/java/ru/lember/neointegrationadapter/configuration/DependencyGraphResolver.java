package ru.lember.neointegrationadapter.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lember.neointegrationadapter.handler.ConditionalTriSplitter;
import ru.lember.neointegrationadapter.handler.Handler;
import ru.lember.neointegrationadapter.handler.Reader;
import ru.lember.neointegrationadapter.handler.Splitter;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.UnaryOperator;

@Slf4j
@Service
public class DependencyGraphResolver {

    private Map<String, Handler> handlersMap;

    @Autowired
    public DependencyGraphResolver(Map<String, Handler> handlersMap) {
        this.handlersMap = handlersMap;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("DependencyGraphResolver initialized");
        handlersMap.forEach((k, v) -> {

            if (v instanceof Reader) {
                Reader reader = (Reader) v;
                String next = firstCharToLowerCaseFunction.apply(reader.next().getClass().getSimpleName());
                log.info("{} -> {}", k, next);
            } else if (v instanceof Splitter) {
                Splitter splitter = (Splitter) v;
                splitter.nextComponents().stream()
                        .map(h -> firstCharToLowerCaseFunction.apply((h.getClass().getSimpleName())))
                        .forEach(h -> log.info("{} -> {}", k, h));

            } else if (v instanceof ConditionalTriSplitter) {
                ConditionalTriSplitter conditionalTriSplitter = (ConditionalTriSplitter) v;
                String first = firstCharToLowerCaseFunction.apply(conditionalTriSplitter.first().getClass().getSimpleName());
                String second = firstCharToLowerCaseFunction.apply(conditionalTriSplitter.second().getClass().getSimpleName());
                String third = firstCharToLowerCaseFunction.apply(conditionalTriSplitter.third().getClass().getSimpleName());

                log.info("{} -> {}", k, first);
                log.info("{} -> {}", k, second);
                log.info("{} -> {}", k, third);
            } else {
                // TODO ConditionalBiSplitter, ConditionalQuadSplitter ...
            }


        });
//        log.info("processor's chains: \n{}",
//                handlersMap.entrySet().stream()
//                        .map(e -> "\n" + e.getKey() + " class: {" + e.getValue().getClass().getSimpleName() + "}")
//                        .collect(Collectors.joining(" -> ")));

    }

    private UnaryOperator<String> firstCharToLowerCaseFunction = s -> s.substring(0, 1).toLowerCase() + s.substring(1);
}
