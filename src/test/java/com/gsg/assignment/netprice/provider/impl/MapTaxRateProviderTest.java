package com.gsg.assignment.netprice.provider.impl;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapTaxRateProviderTest {

  @Autowired
  private MapTaxRateProvider provider;

  @ParameterizedTest(name = "Country {0} should have tax rate of {1}")
  @CsvSource({
      "GB, 0.20",
      "DE, 0.19",
      "FR, 0.20",
      "IT, 0.22"
  })
  @DisplayName("Should return correct tax rate for each country")
  void shouldReturnCorrectTaxRateForCountry(String country, BigDecimal expectedTaxRate) {
    BigDecimal actualTaxRate = provider.getTaxRate(country);
    Assertions.assertEquals(expectedTaxRate, actualTaxRate);
  }

  @ParameterizedTest(name = "Unknown country {0} should have tax rate of 0")
  @CsvSource({
      "US",
      "CA",
      "JP",
      "BR"
  })
  @DisplayName("Should return 0 tax rate for unknown country")
  void shouldReturnZeroTaxRateForUnknownCountry(String country) {
    BigDecimal actualTaxRate = provider.getTaxRate(country);
    Assertions.assertEquals(BigDecimal.ZERO, actualTaxRate);
  }
}
