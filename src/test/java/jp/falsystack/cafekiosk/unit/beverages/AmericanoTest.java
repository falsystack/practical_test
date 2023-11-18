package jp.falsystack.cafekiosk.unit.beverages;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AmericanoTest {

  @Test
  void getName() {
    var americano = new Americano();

    assertThat(americano.getName()).isEqualTo("Americano");
  }

  @Test
  void getPrice() {
    var americano = new Americano();

    assertThat(americano.getPrice()).isEqualTo(400);
  }

}