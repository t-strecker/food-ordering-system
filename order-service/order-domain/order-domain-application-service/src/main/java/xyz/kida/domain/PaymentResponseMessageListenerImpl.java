package xyz.kida.domain;

import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import xyz.kida.domain.dto.message.PaymentResponse;
import xyz.kida.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;

@Slf4j
@ApplicationScoped
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

  @Override
  public void paymentCompleted(PaymentResponse paymentResponse) {

  }

  @Override
  public void paymentCancelled(PaymentResponse paymentResponse) {

  }
}
