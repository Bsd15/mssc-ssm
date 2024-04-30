package guru.springframework.msscssm.services;

import guru.springframework.msscssm.domain.Payment;
import guru.springframework.msscssm.domain.PaymentEvent;
import guru.springframework.msscssm.domain.PaymentState;
import guru.springframework.msscssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

/**
 * Created by jt on 2019-08-10.
 */
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

    @Override
    public Payment newPayment(Payment payment) {
        payment.setState(PaymentState.NEW);
        return paymentRepository.save(payment);
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        return null;
    }

    @Override
    public StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        return null;
    }

    private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        StateMachine<PaymentState, PaymentEvent> stateMachine = stateMachineFactory.getStateMachine(payment.getId().toString());
        stateMachine.stopReactively().subscribe();
        stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> sma.resetStateMachineReactively(
                new DefaultStateMachineContext<>(payment.getState(), null, null, null)
        ).subscribe());
        stateMachine.startReactively().subscribe();
        return stateMachine;
    }
}
