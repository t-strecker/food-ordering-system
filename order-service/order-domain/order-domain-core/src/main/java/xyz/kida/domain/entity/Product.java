package xyz.kida.domain.entity;

import xyz.kida.domain.valueobject.Money;
import xyz.kida.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId>{
  private String name;
  private Money price;

  public Product(ProductId productId, String name, Money price) {
    super.setId(productId);
    this.name = name;
    this.price = price;
  }

  public void updateWithConfirmedNameAndPrice(String name, Money price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public Money getPrice() {
    return price;
  }
}
