package xyz.kida.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
  private final BigDecimal amount;

  public Money(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public boolean isGreaterThanZero() {
    return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
  }

  public boolean isGreaterThan(Money money) {
    return amount != null && amount.compareTo(money.getAmount()) > 0;
  }

  public Money add(Money money) {
    return new Money(setScale(amount.add(money.getAmount())));
  }

  public Money substract(Money money) {
    return new Money(setScale(amount.subtract(money.getAmount())));
  }

  public Money multiply(int multiplier) {
    return new Money(setScale(amount.multiply(new BigDecimal(multiplier))));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Money money = (Money) o;

    return amount.equals(money.amount);
  }

  @Override
  public int hashCode() {
    return amount.hashCode();
  }

  private BigDecimal setScale(BigDecimal input) {
    return input.setScale(2, RoundingMode.HALF_EVEN);
  }
}
