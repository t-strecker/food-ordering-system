package xyz.kida.domain.ports.input.message.listener.payment;

import xyz.kida.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

  void paymentCompleted(PaymentResponse paymentResponse);

  void paymentCancelled(PaymentResponse paymentResponse);

}
