package com.gsg.assignment.netprice.provider;

import java.math.BigDecimal;

public interface TaxRateProvider {
  BigDecimal getTaxRate(String countryIso);
}
