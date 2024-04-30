package guru.springframework.msscssm.config;

import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class StateMachineConfigTest {
    @Autowired
    StateMachineFactory<PaymentState, PaymentEvent> factory;

    @Test
    void testNewStateMachine() {
//        StateMachine<PaymentState, PaymentEvent> sm = factory.getStateMachine(UUID.randomUUID());
//
//        sm.start();
//
//        System.out.println(sm.getState().toString());
//
//        sm.sendEvent(PaymentEvent.PRE_AUTHORIZE);
//
//        System.out.println(sm.getState().toString());
//
//        sm.sendEvent(PaymentEvent.PRE_AUTH_APPROVED);
//
//        System.out.println(sm.getState().toString());
//
//        sm.sendEvent(PaymentEvent.PRE_AUTH_DECLINED);
//
//        System.out.println(sm.getState().toString());

        StateMachine<PaymentState, PaymentEvent> stateMachine = factory.getStateMachine(UUID.randomUUID().toString());
        stateMachine.startReactively().subscribe();
//        log.debug("Initial state: {}", stateMachine.getState().toString());
        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(PaymentEvent.PRE_AUTHORIZE).build())).subscribe();
//        log.debug("state: {}", stateMachine.getState().toString());
        stateMachine.sendEvent(Mono.just(MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_DECLINED).build())).subscribe();
//        log.debug("state: {}", stateMachine.getState().toString());
    }
}