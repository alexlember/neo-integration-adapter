package ru.lember.neointegrationadapter.configuration.projectA;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lember.neointegrationadapter.handler.*;
import ru.lember.neointegrationadapter.handler.projectA.*;
import ru.lember.neointegrationadapter.message.Message;
import ru.lember.neointegrationadapter.message.MessageType;

import javax.annotation.PostConstruct;
import java.util.function.BiFunction;

@Slf4j
@Configuration
public class RoutingConfiguration {

    @PostConstruct
    private void postConstruct() {
        log.info("RoutingConfiguration initialized");
    }

    @Bean
    public Reader testReader1(final Handler testMapper11) {
        return new TestReader1(testMapper11);
    }

    @Bean
    public Mapper testMapper11(final Handler testMapper12) {
        return new TestMapper11(testMapper12);
    }

    @Bean
    public Mapper testMapper12(final Splitter splitter11) {
        return new TestMapper12(splitter11);
    }

    @Bean
    public Splitter splitter11(final Handler testMapper13, final Handler testMapper14, Handler testMapper15) {
        return new Splitter11(testMapper13, testMapper14, testMapper15);
    }

    @Bean
    public Mapper testMapper13(final Handler testWriter1) {
        return new TestMapper13(testWriter1);
    }

    @Bean
    public Mapper testMapper14(final Handler testWriter1) {
        return new TestMapper14(testWriter1);
    }

    @Bean
    public Mapper testMapper15(final Handler testWriter2) {
        return new TestMapper15(testWriter2);
    }

    @Bean
    public Reader testReader2(final Handler testMapper21) {
        return new TestReader2(testMapper21);
    }

    @Bean
    public Mapper testMapper21(final ConditionalTriSplitter conditionalTriSplitter21) {
        return new TestMapper21(conditionalTriSplitter21);
    }

    @Bean
    public ConditionalTriSplitter conditionalTriSplitter21(final Handler testMapper22, final Handler testMapper23, Handler testMapper24) {

        BiFunction<Message, ConditionalTriSplitter, Handler> switchFunc = (m, s) -> {
            if (MessageType.NONE == m.getType()) {
                return s.first();
            } else if (MessageType.ADD == m.getType()) {
                return s.second();
            }
            return s.third();
        };

        return new ConditionalSplitter2(testMapper22, testMapper23, testMapper24, switchFunc);
    }

    @Bean
    public Mapper testMapper22(final Handler testWriter1) {
        return new TestMapper22(testWriter1);
    }

    @Bean
    public Mapper testMapper23(final Handler testWriter2) {
        return new TestMapper23(testWriter2);
    }

    @Bean
    public Mapper testMapper24(final Handler testWriter2) {
        return new TestMapper24(testWriter2);
    }

    @Bean
    public Reader testReader3(final Handler testWriter2) {
        return new TestReader3(testWriter2);
    }

    @Bean
    public Writer testWriter1() {
        return new TestWriter1();
    }

    @Bean
    public Writer testWriter2() {
        return new TestWriter2();
    }
}
