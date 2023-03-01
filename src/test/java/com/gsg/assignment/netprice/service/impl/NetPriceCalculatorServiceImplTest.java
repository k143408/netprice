package com.gsg.assignment.netprice.service.impl;

import static org.mockito.Mockito.when;

import com.gsg.assignment.netprice.exception.CountryNotFoundException;
import com.gsg.assignment.netprice.provider.TaxRateProvider;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NetPriceCalculatorServiceImplTest {

  @Mock
  private TaxRateProvider taxRateProvider;

  @InjectMocks
  private NetPriceCalculatorServiceImpl calculator;

  @ParameterizedTest
  @DisplayName("Should calculate net price correctly with valid tax rate")
  @CsvSource({"DE, 100.00, 0.19, 84.03", "GB, 50.00, 0.05, 47.62", "FR, 1.99, 0.20, 1.66"})
  void shouldCalculateNetPriceWithValidTaxRate(String countryIso, BigDecimal grossPrice,
      BigDecimal taxRate, BigDecimal expectedNetPrice) {
    when(taxRateProvider.getTaxRate(countryIso)).thenReturn(taxRate);
    Number actualNetPrice = calculator.calculateNetPrice(grossPrice, countryIso);
    Assertions.assertEquals(expectedNetPrice, actualNetPrice);
  }

  @ParameterizedTest
  @DisplayName("Should throw exception with invalid country code")
  @CsvSource({"XX, 100.00", "ZZ, 50.00", "YY, 200.00"})
  void shouldThrowExceptionWithInvalidCountryCode(String countryIso, BigDecimal grossPrice) {
    when(taxRateProvider.getTaxRate(countryIso)).thenReturn(BigDecimal.ZERO);
    Assertions.assertThrows(CountryNotFoundException.class,
        () -> calculator.calculateNetPrice(grossPrice, countryIso));
  }
}
