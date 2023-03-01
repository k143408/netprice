package com.gsg.assignment.netprice.service.impl;

import com.gsg.assignment.netprice.exception.CountryNotFoundException;
import com.gsg.assignment.netprice.provider.TaxRateProvider;
import com.gsg.assignment.netprice.service.NetPriceCalculatorService;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class NetPriceCalculatorServiceImpl implements NetPriceCalculatorService {

  private final TaxRateProvider taxRateProvider;

  public NetPriceCalculatorServiceImpl(TaxRateProvider taxRateProvider) {
    this.taxRateProvider = taxRateProvider;
  }

  @Override
  public BigDecimal calculateNetPrice(BigDecimal grossPrice, String countryIso) {
    BigDecimal taxRate = taxRateProvider.getTaxRate(countryIso);
    if (BigDecimal.ZERO.equals(taxRate)) {
      throw new CountryNotFoundException("Invalid country code: " + countryIso);
    }
    BigDecimal netPrice = grossPrice.divide(BigDecimal.ONE.add(taxRate), 2, BigDecimal.ROUND_HALF_UP);
    return netPrice;
  }
}
